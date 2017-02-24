package com.dpi.financial.ftcom.model.to.meb.atm.journal;

import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.SwipeCard;
import com.dpi.financial.ftcom.model.type.atm.journal.JournalFileState;

import javax.persistence.*;
import java.util.Date;

/**
 * JournalContent entity persist physical journal file lines in database
 *
 * @since ver 1.0.0 modified by Hossein Mohammadi w.r.t Issue #1 as on Monday, December 05, 2016
 * <li>Prepare ATM transactions based on journal content</li>
 */
@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "MEB_JOURNAL_CONTENT_SEQ")
@Table(name = "MEB_JOURNAL_CONTENT", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LUNO", "NAME", "LINE_NUMBER"}),
        @UniqueConstraint(columnNames = {"LUNO", "FILE_NAME", "LINE_NUMBER"}),
        @UniqueConstraint(columnNames = {"LUNO", "JOURNAL_DATE", "LINE_NUMBER"})
})
@NamedQueries({
        @NamedQuery(name = JournalContent.FIND_ALL, query = "select t from JournalContent t where t.deleted = false"),
        // @NamedQuery(name = JournalContent.FIND_BY_TERMINAL, query = "select t from JournalContent t where t.deleted = false and t.terminal = :terminal order by t.journalDate asc")
})
public class JournalContent extends EntityBase {
    public static final String FIND_ALL = "JournalContent.findAll";
    public static final String FIND_BY_TERMINAL = "JournalContent.findByTerminal";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOURNAL_FILE_ID")
    private JournalFile journalFile;

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

    @Column(name = "LINE_NUMBER", nullable = false)
    private int lineNumber;

    @Column(name = "LINE", length = 400)
    private String line;

    public JournalFile getJournalFile() {
        return journalFile;
    }

    public void setJournalFile(JournalFile journalFile) {
        this.journalFile = journalFile;
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

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}