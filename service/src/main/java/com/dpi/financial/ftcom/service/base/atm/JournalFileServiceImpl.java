package com.dpi.financial.ftcom.service.base.atm;

import com.dpi.financial.ftcom.api.base.atm.JournalFileService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.atm.journal.JournalFileDao;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.atm.journal.JournalFile;
import com.dpi.financial.ftcom.model.type.atm.journal.JournalFileState;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;
import com.dpi.financial.ftcom.utility.date.DateUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@Local(JournalFileService.class)
public class JournalFileServiceImpl extends GeneralServiceImpl<JournalFile>
        implements JournalFileService {

    @EJB
    private JournalFileDao dao;

    @Override
    public GenericDao<JournalFile> getGenericDao() {
        return dao;
    }

    @Override
    public List<JournalFile> findAll(Terminal terminal) {
        return dao.findAll(terminal);
    }

    /**
     * This method sync files on disk to database JOURNAL_FILE and
     * return list of journal file info in journal date order.
     *
     * @param baseFolder
     * @param terminal
     * @return
     * @since ver 1.0.0 modified by Hossein Mohammadi w.r.t Issue #1 as on Monday, December 05, 2016
     * <li>Prepare ATM transactions based on journal content</li>
     */
    @Override
    public List<JournalFile> getJournalFileList(String baseFolder, Terminal terminal) {
        List<JournalFile> journalFiles = findAll(terminal);

        JournalPhysicalFile journalPhysicalFile = new JournalPhysicalFile(baseFolder);
        try {
            List<File> journalPhysicalFiles = journalPhysicalFile.getOrderedJournalFiles(terminal.getLuno());
            for (File file : journalPhysicalFiles) {
                JournalFile journal = new JournalFile();

                journal.setLuno(terminal.getLuno());
                journal.setName(FilenameUtils.getBaseName(file.getName()));
                journal.setFileName(file.getName());
                journal.setTerminal(terminal);
                updateFileInfo(journal, file);

                journal.setState(JournalFileState.ENTRY);

                // http://stackoverflow.com/questions/13138990/how-to-search-in-a-list-of-java-object
                /*
                List<JournalFile> result = journalFiles.stream()
                        .filter(item -> item.getFileName().equals(journal.getFileName()))
                        .collect(Collectors.toList());
                */
                Optional<JournalFile> any = journalFiles.stream()
                        .filter(item -> item.getFileName().equals(journal.getFileName()))
                        .findAny();

                if (any.isPresent()) {
                    JournalFile journalFile = any.get();
                    System.out.println(
                            MessageFormat.format("Journal {0}/{1} found.", journalFile.getLuno(), journalFile.getFileName())
                    );
                    System.out.println(
                            MessageFormat.format("{0}/{1}", journalFile.getMd5(), journal.getMd5())
                    );
                    if (!journalFile.getMd5().equals(journal.getMd5())) {
                        System.out.println(
                                MessageFormat.format("Journal {0}/{1} content is different.", journalFile.getLuno(), journalFile.getFileName())
                        );
                        updateFileInfo(journalFile, file);
                        journalFile.setState(JournalFileState.ENTRY);
                        update(journalFile);
                    }
                } else {
                    JournalFile journalFile = create(journal);
                    System.out.println(
                            MessageFormat.format("Journal {0}/{1} added.", journalFile.getLuno(), journalFile.getFileName())
                    );
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        journalFiles = findAll(terminal);
        return journalFiles;
    }

    @Override
    public List<JournalFile> getJournalFileList(Terminal terminal, Date journalDateFrom, Date journalDateTo) {
        return findAll(terminal).stream()
                .filter(item -> DateUtil.isBetweenDate(item.getJournalDate(), journalDateFrom, journalDateTo))
                .collect(Collectors.toList());
    }

    private JournalFile updateFileInfo(JournalFile journal, File file) {
        try {
            DateFormat format = new SimpleDateFormat("yyyyMMdd");
            String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
            journal.setJournalDate(format.parse(fileNameWithOutExt));

            Path path = Paths.get(file.getCanonicalPath());
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);

            journal.setCreationTime(new Date(attr.creationTime().toMillis()));
            journal.setLastAccessTime(new Date(attr.lastAccessTime().toMillis()));
            journal.setLastModifiedTime(new Date(attr.lastModifiedTime().toMillis()));

            journal.setDirectory(attr.isDirectory());
            journal.setOther(attr.isOther());
            journal.setRegularFile(attr.isRegularFile());
            journal.setSymbolicLink(attr.isSymbolicLink());

            journal.setSize(attr.size());

            journal.setDigest(getDigest(path));
            journal.setMd5(encodeToMd5(file.getAbsolutePath()));

        } catch (ParseException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return journal;
    }

    /**
     * see whats going on calcMD5
     *
     * @param file
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    private byte[] getDigest(Path file) throws IOException, NoSuchAlgorithmException {
        MessageDigest algorithm = MessageDigest.getInstance("MD5");
        try (InputStream is = Files.newInputStream(file);
             DigestInputStream dis = new DigestInputStream(is, algorithm)) {
            /* Read decorated stream (dis) to EOF as normal... */
            byte[] buffer = new byte[8192];
            while (dis.read(buffer) != -1)
                ;
        }
        byte[] digest = algorithm.digest();
        // System.out.println("Digest: " + digest);
        return digest;
    }

    public String encodeToMd5(String filePath) throws NoSuchAlgorithmException, IOException {

        MessageDigest md = MessageDigest.getInstance("SHA1");
        FileInputStream fis = new FileInputStream(filePath);

        byte[] dataBytes = new byte[1024];

        int nread = 0;

        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }

        byte[] mdbytes = md.digest();

        // convert the byte to hex format
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        System.out.println("Digest(in hex format):: " + sb.toString());
        return sb.toString();
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

    public String calcMD5() throws Exception {
        byte[] buffer = new byte[8192];
        MessageDigest md = MessageDigest.getInstance("MD5");

        DigestInputStream dis = new DigestInputStream(new FileInputStream(new File("Path to file")), md);
        try {
            while (dis.read(buffer) != -1) ;
        } finally {
            dis.close();
        }

        byte[] bytes = md.digest();

        // bytesToHex-method
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            /* comment by Hossein - uncomment it
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            */
        }

        return new String(hexChars);
    }
}
