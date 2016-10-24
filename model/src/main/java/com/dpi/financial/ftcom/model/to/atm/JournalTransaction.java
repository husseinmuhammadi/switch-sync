package com.dpi.financial.ftcom.model.to.atm;

import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.converter.*;
import com.dpi.financial.ftcom.model.type.*;
import com.dpi.financial.ftcom.utility.helper.DateHelper;
import net.sf.ehcache.constructs.nonstop.concurrency.LockOperationTimedOutNonstopException;

import javax.persistence.*;
import java.util.Date;

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "JOURNAL_TRANSACTION_SEQ")
@Table(name = "JOURNAL_TRANSACTION", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LUNO", "NAME", "START_INDEX"})
})
@NamedQueries({
        @NamedQuery(name = JournalTransaction.FIND_ALL, query = "select t from JournalTransaction t where t.deleted = false")
})
public class JournalTransaction extends EntityBase {
    public static final String FIND_ALL = "JournalTransaction.findAll";

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
    @Column(name = "TRANSACTION_DATE", nullable = false)
    private Date transactionDate;

    @Column(name = "START_INDEX", nullable = false)
    private Long startIndex;
}