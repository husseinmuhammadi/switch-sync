package com.dpi.financial.ftcom.model.to.atm;

import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.converter.*;
import com.dpi.financial.ftcom.model.type.*;
import com.dpi.financial.ftcom.utility.helper.DateHelper;
import net.sf.ehcache.constructs.nonstop.concurrency.LockOperationTimedOutNonstopException;

import javax.persistence.*;
import java.nio.file.attribute.FileTime;
import java.util.Date;

/**
 * JournalFile entity persist physical journal file information on database
 * @since ver 1.0.0 modified by Hossein Mohammadi w.r.t Issue #1 as on Monday, December 05, 2016
 *  <li>Prepare ATM transactions based on journal content</li>
 */
@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "JOURNAL_FILE_SEQ")
@Table(name = "JOURNAL_FILE", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LUNO", "NAME"}),
        @UniqueConstraint(columnNames = {"LUNO", "FILE_NAME"}),
        @UniqueConstraint(columnNames = {"LUNO", "JOURNAL_DATE"})
})
@NamedQueries({
        @NamedQuery(name = JournalFile.FIND_ALL, query = "select t from JournalFile t where t.deleted = false"),
        @NamedQuery(name = JournalFile.FIND_BY_TERMINAL, query = "select t from JournalFile t where t.deleted = false and t.terminal = :terminal order by t.journalDate asc")
})
public class JournalFile extends EntityBase {
    public static final String FIND_ALL = "JournalFile.findAll";
    public static final String FIND_BY_TERMINAL = "JournalFile.findByTerminal";

    @OneToOne
    @JoinColumn(name = "TERMINAL_ID")
    private Terminal terminal;

    // Logical Unit Number
    @Column(name = "LUNO", nullable = false, length = 10)
    private String luno;

    @Column(name = "NAME", nullable = false, length = 8)
    private String name;

    @Column(name = "FILE_NAME", nullable = false, length = 12)
    private String fileName;

    @Temporal(TemporalType.DATE)
    @Column(name = "JOURNAL_DATE", nullable = false)
    private Date journalDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATION_TIME")
    private Date creationTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_ACCESS_TIME")
    private Date lastAccessTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFIED_TIME")
    private Date lastModifiedTime;

    @Column(name = "IS_DIRECTORY")
    private boolean directory;

    @Column(name = "IS_OTHER")
    private boolean other;

    @Column(name = "IS_REGULAR_FILE")
    private boolean regularFile;

    @Column(name = "IS_SYMBOLIC_LINK")
    private boolean symbolicLink;

    @Column(name = "FILE_SIZE")
    private Long size;

    @Column(name = "DIGEST", columnDefinition = "RAW(32) NOT NULL")
    private byte[] digest;

    /*
    @Column(name = "DIGEST", nullable = false, length = 32)
    private String digest;
    */

    @Column(name = "MESSAGE_DIGEST_ALGORITHM_5", nullable = false, length = 40)
    private String md5;

    @Column(name = "IS_PREPARED", nullable = false, columnDefinition = "NUMBER(1,0) default 0")
    private boolean prepared = false;

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public String getLuno() {
        return luno;
    }

    public void setLuno(String luno) {
        this.luno = luno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getJournalDate() {
        return journalDate;
    }

    public void setJournalDate(Date journalDate) {
        this.journalDate = journalDate;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

    public boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public boolean isRegularFile() {
        return regularFile;
    }

    public void setRegularFile(boolean regularFile) {
        this.regularFile = regularFile;
    }

    public boolean isSymbolicLink() {
        return symbolicLink;
    }

    public void setSymbolicLink(boolean symbolicLink) {
        this.symbolicLink = symbolicLink;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getDigest() {
        return digest;
    }

    public void setDigest(byte[] digest) {
        this.digest = digest;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public boolean isPrepared() {
        return prepared;
    }

    public void setPrepared(boolean prepared) {
        this.prepared = prepared;
    }
}