package com.dpi.financial.ftcom.model.to.atm;

import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.converter.*;
import com.dpi.financial.ftcom.model.type.*;
import com.dpi.financial.ftcom.utility.helper.DateHelper;
import net.sf.ehcache.constructs.nonstop.concurrency.LockOperationTimedOutNonstopException;

import javax.persistence.*;
import java.util.Date;

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "JOURNAL_FILE_SEQ")
@Table(name = "JOURNAL_FILE", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LUNO", "NAME"}),
        @UniqueConstraint(columnNames = {"LUNO", "FILE_NAME"}),
        @UniqueConstraint(columnNames = {"LUNO", "JOURNAL_DATE"})
})
@NamedQueries({
        @NamedQuery(name = JournalFile.FIND_ALL, query = "select t from JournalFile t where t.deleted = false")
})
public class JournalFile extends EntityBase {
    public static final String FIND_ALL = "JournalFile.findAll";

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
}