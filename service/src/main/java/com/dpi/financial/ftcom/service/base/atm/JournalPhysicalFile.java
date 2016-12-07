package com.dpi.financial.ftcom.service.base.atm;

import com.dpi.financial.ftcom.model.to.atm.JournalFile;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class JournalPhysicalFile {

    private final String baseFolder;

    public JournalPhysicalFile(String baseFolder) {
        this.baseFolder = baseFolder;
    }

    private File getTerminalPath(String luno) throws FileNotFoundException {
        final File parent = new File(baseFolder);

        List<File> folders = Arrays.asList(parent.listFiles());

        Optional<File> any = folders.stream()
                .filter(item -> item.isDirectory() && item.getName().equals(luno))
                .findAny();

        if (!any.isPresent())
            throw new FileNotFoundException(
                    MessageFormat.format("Journal path not found for terminal {0}.", luno)
            );

        return any.get();
    }

    /**
     * This method
     *
     * @param luno
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public File getJournalFile(String luno, String fileName) throws FileNotFoundException {
        List<File> files = getOrderedJournalFiles(luno);

        Optional<File> any = files.stream()
                .filter(item -> item.getName().equals(fileName))
                .findAny();

        if (!any.isPresent())
            throw new FileNotFoundException(
                    MessageFormat.format("Journal file {0}/{1} not found.", luno, fileName)
            );

        return any.get();
    }

    /**
     * This method populate list of journal files for specified terminal folder
     *
     * @param luno
     * @return
     */
    public List<File> getOrderedJournalFiles(String luno) throws FileNotFoundException {
        List<File> jrnFiles = new ArrayList<File>();
        final File terminalJournalFolder = getTerminalPath(luno);

        if (terminalJournalFolder.isDirectory()) {
            jrnFiles = Arrays.asList(terminalJournalFolder.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return FilenameUtils.getExtension(pathname.getName()).equalsIgnoreCase("jrn");
                }
            }));

            // http://stackoverflow.com/questions/122105/what-is-the-best-way-to-filter-a-java-collection
            jrnFiles = jrnFiles.stream()
                    .filter(item -> item.isFile() &&
                                    FilenameUtils.getExtension(item.getName()).equalsIgnoreCase("jrn") &&

                                    // ^\d{4}-\d{2}-\d{2}$ regex matches for YYYY-MM-DD
                                    // With the matches method, the anchors ^ and $ (beginning and end of string, respectively) are present implicitly.
                                    FilenameUtils.getBaseName(item.getName()).matches("\\d{4}\\d{2}\\d{2}")
                    ).collect(Collectors.toList());

            // http://stackoverflow.com/questions/16252269/how-to-sort-a-list-arraylist-in-java
            // Collections.sort(List<T> list, Comparator<? super T> c)
            Collections.sort(jrnFiles, (f1, f2) -> f1.getName().compareTo(f2.getName()));
        }

        return jrnFiles;
    }

    @Deprecated
    private List<File> getOrderedJournalFiles2(File terminalJournalFolder) {
        List<File> jrnFiles = new ArrayList<File>();

        if (terminalJournalFolder.isDirectory()) {

            File[] files = terminalJournalFolder.listFiles();

            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File f1, File f2) {
                    return f1.getName().compareTo(f2.getName());
                }
            });

            for (final File journal : files) {
                if (journal.isFile()) {
                    String ext = FilenameUtils.getExtension(journal.getAbsolutePath());
                    if (ext != null && ext.equalsIgnoreCase("jrn"))
                        jrnFiles.add(journal);
                }
            }
        }

        return jrnFiles;
    }

    public Map<String, File> getAtmJournalFolderMap() {
        Map<String, File> atmJournalFolderMap = new HashMap<String, File>();

        try {
            final File parent = new File(baseFolder);

            for (final File folder : parent.listFiles()) {
                if (folder.isDirectory())
                    atmJournalFolderMap.put(folder.getName(), folder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return atmJournalFolderMap;
    }

    public void uploadAllFiles() throws IOException {
        Map<String, File> mp = getAtmJournalFolderMap();
        // Iterating the hash map
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println("Uploading atm journals " + pairs.getKey());
            File folder = (File) pairs.getValue();
            for (File journal : getOrderedJournalFiles(folder.getName())) {
                uploadContent(journal);
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
    }


    public void uploadContent(final File file) throws IOException {
        /**
         SequentialStreamService sequentialStreamService = new SequentialStreamService();
         BufferedReader br = null;
         try {

         String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
         String terminal = file.getParent().substring(file.getParent().lastIndexOf("\\") + 1);
         br = new BufferedReader(new FileReader(file));

         String line;
         boolean processed = false;

         int startIndex = 0;
         startIndex = getMaxLineNumber(terminal, fileNameWithOutExt);

         for (int i = 0; i < startIndex; i++) {
         line = br.readLine();
         }

         List<String> list = new ArrayList<String>(DataMgr.maxTransaction);
         int index = startIndex;

         while ((line = br.readLine()) != null) {
         processed = true;

         list.add(line);
         index++;

         // index value is for next line
         if (index % DataMgr.maxTransaction == 0) {
         sequentialStreamService.saveLines(terminal, fileNameWithOutExt, list, startIndex);

         startIndex += DataMgr.maxTransaction;
         list = new ArrayList<String>(DataMgr.maxTransaction);

         System.out.println(DataMgr.maxTransaction + " line is processed");
         }
         }

         if (index > startIndex)
         sequentialStreamService.saveLines(terminal, fileNameWithOutExt, list, startIndex);

         if (processed)
         System.out.println(file.getName() + " processed successfully.");

         } catch (FileNotFoundException e) {
         e.printStackTrace();
         } catch (IOException e) {
         e.printStackTrace();
         } finally {
         if (br != null)
         br.close();

         }
         */
    }

    public List<JournalFile> getJournalFileList() throws FileNotFoundException {

        List<JournalFile> journalFiles = new ArrayList<JournalFile>();

        Map<String, File> journalFolderMap = getAtmJournalFolderMap();

        Iterator it = journalFolderMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();

            File folder = (File) pairs.getValue();
            for (File jrnFile : getOrderedJournalFiles(folder.getName())) {
                JournalFile journalFile = new JournalFile();

                jrnFile.getName();

                journalFile.setLuno(folder.getName());
                journalFile.setName(FilenameUtils.getBaseName(jrnFile.getName()));
                journalFile.setFileName(jrnFile.getName());

                try {
                    DateFormat format = new SimpleDateFormat("yyyyMMdd");
                    String fileNameWithOutExt = FilenameUtils.removeExtension(jrnFile.getName());
                    journalFile.setJournalDate(format.parse(fileNameWithOutExt));

                    Path file = Paths.get(jrnFile.getCanonicalPath());

                    BasicFileAttributes attr =
                            Files.readAttributes(file, BasicFileAttributes.class);
                    journalFile.setCreationTime(new Date(attr.creationTime().toMillis()));
                    journalFile.setLastAccessTime(new Date(attr.lastAccessTime().toMillis()));
                    journalFile.setLastModifiedTime(new Date(attr.lastModifiedTime().toMillis()));

                    /*
                    journalFile.setDirectory(attr.isDirectory());
                    journalFile.setOther(attr.isOther());
                    journalFile.setRegularFile(attr.isRegularFile());
                    journalFile.setSymbolicLink(attr.isSymbolicLink());
                    */
                    journalFile.setSize(attr.size());

                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                journalFiles.add(journalFile);
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        return journalFiles;
    }
}
