package com.dpi.financial.ftcom.service.base.atm;

import com.dpi.financial.ftcom.api.base.atm.JournalFileService;
import com.dpi.financial.ftcom.model.dao.atm.JournalFileDao;
import com.dpi.financial.ftcom.model.to.atm.JournalFile;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.authz.HostUnauthorizedException;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.el.CompositeELResolver;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Stateless
@Local(JournalFileService.class)
public class JournalFileServiceImpl implements JournalFileService {

    @EJB
    private JournalFileDao dao;

    @Override
    public JournalFile create(JournalFile journalFile) {
        return dao.create(journalFile);
    }

    @Override
    public List<JournalFile> findAll() {
        return dao.findAll();
    }

    @Override
    public JournalFile find(Long id) {
        return dao.findById(id);
    }

    @Override
    public void update(JournalFile journalFile) {
        dao.update(journalFile);
    }

    @Override
    public void delete(JournalFile journalFile) {
        dao.remove(journalFile);
    }

    @Override
    public List<JournalFile> findAll(Terminal terminal) {
        return dao.findAll(terminal);
    }

    @Override
    public List<JournalFile> getJournalFileList(String path) {

        List<JournalFile> journalFiles = new ArrayList<JournalFile>();

        Map<String, File> journalFolderMap = getAtmJournalFolderMap(path);

        Iterator it = journalFolderMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();

            File folder = (File) pairs.getValue();
            for (File jrnFile : getOrderedJournalFiles(folder)) {
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

    public Map<String, File> getAtmJournalFolderMap(String baseFolder) {
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

    public List<File> getOrderedJournalFiles(File terminalJournalFolder) {
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
                    // System.out.println(journalFile.getAbsolutePath());
                }
            }
        }

        return jrnFiles;
    }


    public void uploadAllFiles(String atmJournalCollectionFolder) throws IOException {
        Map<String, File> mp = getAtmJournalFolderMap(atmJournalCollectionFolder);
        // Iterating the hash map
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println("Uploading atm journals " + pairs.getKey());
            File folder = (File) pairs.getValue();
            for (File journal : getOrderedJournalFiles(folder)) {
                uploadContent(journal);
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    public void listFilesForFolder(final File folder, final boolean recurse) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory() && recurse) {
                listFilesForFolder(fileEntry, recurse);
            } else {
                System.out.println(fileEntry.getAbsolutePath());
                System.out.println(fileEntry.getParent() + "\\" + fileEntry.getName());
            }
        }
    }

    int getLineNumber(File file) throws IOException {
        BufferedReader reader = null;
        int lines = 0;
        try {
            reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
            while (reader.readLine() != null)
                lines++;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                reader.close();
        }
        return lines;
    }

    int getLineNumber2(File file) throws IOException {
        LineNumberReader reader = null;
        try {
            reader = new LineNumberReader(new FileReader(file.getAbsolutePath()));
            while ((reader.readLine()) != null)
                ;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                reader.close();
        }
        return reader.getLineNumber();
    }

    public int countLineNumber(File file) throws IOException {
        // http://www.technicalkeeda.com/java/how-to-count-total-number-of-lines-of-file-using-java
        LineNumberReader lineNumberReader = null;
        int lines = 0;
        try {
            lineNumberReader = new LineNumberReader(new FileReader(file));
            lineNumberReader.skip(Long.MAX_VALUE);
            lines = lineNumberReader.getLineNumber();

        } catch (IOException e) {
            System.out.println("IOException Occured" + e.getMessage());
        } finally {
            if (lineNumberReader != null)
                lineNumberReader.close();
        }

        return lines;
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

    private int getMaxLineNumber(String terminal, String fileNameWithOutExt) {
        int result = 0;

        /**
         PreparedStatement ps = null;
         try {
         String sqlQry = "SELECT MAX(LINENUMBER) FROM ATM_OPERATION_MESSAGE O WHERE O.TERMINALID = ? AND O.FILENAME = ?";
         ps = DataMgr.conn.prepareStatement(sqlQry);
         ps.setString(1, terminal);
         ps.setString(2, fileNameWithOutExt);
         ResultSet rs = ps.executeQuery();
         while (rs.next()) {
         result = rs.getInt(1);
         }
         } catch (SQLException e) {
         e.printStackTrace();
         } finally {
         try {
         if (ps != null)
         ps.close();
         } catch (SQLException e) {
         e.printStackTrace();
         }
         }
         */

        return result;
    }
}
