package com.dpi.financial.ftcom.web.controller.conf;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Created by Hossein on 28/11/2016.
 */
public class JournalWatcher implements Runnable {

    private final Path journalPath;
    private Thread thread;
    private WatchService watcher;

    public JournalWatcher(Path journalPath) {
        this.journalPath = journalPath;
    }

    @Override
    public void run() {
        // System.out.println(Thread.currentThread().getName());

        WatchKey key;

        try {
            watcher = FileSystems.getDefault().newWatchService();
            key = journalPath.register(watcher,
                    ENTRY_CREATE,
                    ENTRY_DELETE,
                    ENTRY_MODIFY);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Monitoring path:/" + journalPath);

        while (true) {
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

                try {
                    System.out.print("Notify " + filename);
                    if (ENTRY_CREATE == kind) {
                        // A new path was created
                        System.out.println(" created.");
                    } else if (ENTRY_MODIFY == kind) {
                        // An existing path modified
                        System.out.println(" modified.");
                    } else if (ENTRY_DELETE == kind) {
                        // An existing path deleted
                        System.out.println(" deleted.");
                    }

                    // Resolve the filename against the directory.
                    Path child = journalPath.resolve(filename);

                    String contentType = Files.probeContentType(child);

                    // Verify that the new file is a text file.
                    if (contentType != null && !contentType.equals("text/plain")) {
                        System.err.format("New file '%s' is not a plain text file.%n", filename);
                        continue;
                    }

                    PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:.jrn");
                    if (!matcher.matches(filename)) {
                        // System.out.println("File is not a kind of JRN: " + filename);
                        continue;
                    }



                    System.out.println("...");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println(e);
                    continue;
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
    }

    public void watch() {
        if ((thread == null) || (!thread.isAlive())) {
            thread = new Thread(this, "JournalWatcher" + journalPath.getFileName());
            thread.start();
        }
    }

    protected void destroy() {
        System.out.println("JournalWatcher destroy() ... [" + journalPath.getFileName() + "]");
        try {
            // thread.doShutdown();
            if (thread != null) {
                thread.interrupt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
