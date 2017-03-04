package com.dpi.financial.ftcom.web.controller.conf;

import com.dpi.financial.ftcom.api.base.atm.TerminalService;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.utility.system.OperationSystem;
import com.dpi.financial.ftcom.web.exception.UnsupportedOperationSystem;
import org.omnifaces.cdi.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static java.nio.file.StandardWatchEventKinds.*;

@Named
@Startup
public class AtmConfiguration implements Runnable {
    Logger logger = LoggerFactory.getLogger(AtmConfiguration.class);

    @EJB
    private TerminalService terminalService;

    private List<JournalWatcher> journalDirectoryWatcher;

    Thread thread = null;
    private WatchService watcher;
    private Map<String, Terminal> terminalMap;

    public String getJournalPath() throws UnsupportedOperationSystem {
        String path = null;

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("atm.properties");
            Properties properties = new Properties();
            properties.load(input);
            switch (OperationSystem.getOS()) {
                case Windows:
                    path = properties.getProperty("JOURNAL_PATH_WINDOWS");
                    break;
                case Unix:
                    path = properties.getProperty("JOURNAL_PATH_UNIX");
                    break;
                case Solaris:
                case Mac:
                case unsupported:
                    throw new UnsupportedOperationSystem();
            }
            logger.info("Operation System: {}", OperationSystem.getOS());
        } catch (IOException e) {
            logger.error("IOException", e);
        }

        return path;
    }

    /**
     * What journal path
     *
     * @param journalPath
     * @throws IOException
     */
    public void watch(Path journalPath) throws IOException {
        WatchKey key;

        try {
            watcher = FileSystems.getDefault().newWatchService();

            key = journalPath.register(watcher,
                    ENTRY_CREATE,
                    ENTRY_DELETE,
                    ENTRY_MODIFY);
            System.out.println("Monitoring path:/" + journalPath);

            for (; ; ) {
                System.out.println("Wait for key to be signaled ...");
                // wait for key to be signaled
                try {
                    key = watcher.take();
                } catch (InterruptedException x) {
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    // This key is registered only for ENTRY_CREATE events,
                    // but an OVERFLOW event can occur regardless if events
                    // are lost or discarded.
                    if (kind == OVERFLOW) {
                        continue;
                    }

                    // The filename is the context of the event.
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();

                    // Verify that the new
                    //  file is a text file.
                    try {
                        // Resolve the filename against the directory.
                        // If the filename is "test" and the directory is "foo",
                        // the resolved name is "test/foo".
                        Path child = journalPath.resolve(filename);
                        System.out.println("Watch Event Context: " + filename);
                        System.out.println("Directory resolve: " + child);

                        boolean isDirectory = Files.isDirectory(child);
                        if (!isDirectory)
                            continue;

                        String contentType = Files.probeContentType(child);

                        if (contentType != null && !contentType.equals("text/plain")) {
                            System.err.format("New file '%s' is not a plain text file.%n", filename);
                            continue;
                        }

                        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:.jrn");
                        if (!matcher.matches(filename)) {
                            System.out.println("File is not a kind of JRN: " + filename);
                            continue;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        System.err.println(e);
                        continue;
                    }

                    if (ENTRY_CREATE == kind) {
                        // A new path was created
                        System.out.println("File created: " + filename);
                    } else if (ENTRY_MODIFY == kind) {
                        // An existing path modified
                        System.out.println("File modified: " + filename);
                    } else if (ENTRY_DELETE == kind) {
                        // An existing path deleted
                        System.out.println("File deleted: " + filename);
                    }
                }

                // Reset the key -- this step is critical if you want to
                // receive further watch events.  If the key is no longer valid,
                // the directory is inaccessible so exit the loop.
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void run() {
        try {
            String journalPath = getJournalPath();

            Path dir = Paths.get(journalPath);
            logger.info("Journal path: " + journalPath);

            registerAll(dir);

            // watch(dir);
        } catch (IOException e) {
            logger.error("IOException", e);
        } catch (UnsupportedOperationSystem e) {
            logger.error("UnsupportedOperationSystem", e);
        }
    }

    @PostConstruct
    protected void init() {
        List<Terminal> terminals = terminalService.findAll();
        terminalMap = new HashMap<String, Terminal>(terminals.size());
        for (Terminal terminal : terminals) {
            terminalMap.put(terminal.getLuno(), terminal);
        }

        journalDirectoryWatcher = new ArrayList<JournalWatcher>();

        System.out.println("AtmConfiguration init() ...");
        if ((thread == null) || (!thread.isAlive())) {
            thread = new Thread(this);
            thread.start();
        }
    }

    @PreDestroy
    protected void destroy() {
        logger.info("AtmConfiguration destroy() ...");
        try {
            // thread.doShutdown();
            for (JournalWatcher watcher : journalDirectoryWatcher) {
                watcher.destroy();
            }

            if (thread != null) {
                thread.interrupt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Register the given directory, and all its sub-directories, with the WatchService.
     */
    private void registerAll(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start,
                new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                            throws IOException {

                        if (terminalMap.get(dir.getFileName().toString()) != null) {
                            System.out.println("Path " + dir.getFileName() + " watched ...");
                            JournalWatcher watcher = new JournalWatcher(dir);
                            watcher.watch();
                            journalDirectoryWatcher.add(watcher);
                        } else {
                            System.out.println("Path " + dir.getFileName() + " ignored ...");
                        }

                        return FileVisitResult.CONTINUE;
                    }
                }
        );
    }
}
