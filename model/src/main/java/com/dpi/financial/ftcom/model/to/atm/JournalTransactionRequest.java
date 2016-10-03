package com.dpi.financial.ftcom.model.to.atm;

import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.converter.*;
import com.dpi.financial.ftcom.model.type.*;
import com.dpi.financial.ftcom.utility.helper.DateHelper;
import net.sf.ehcache.constructs.nonstop.concurrency.LockOperationTimedOutNonstopException;

import javax.persistence.*;
import java.util.Date;

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "JOURNAL_SEQ")
@Table(name = "JOURNAL",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LUNO"})
})
@NamedQueries({
        @NamedQuery(name = JournalTransactionRequest.FIND_ALL, query = "select t from JournalTransactionRequest t where t.deleted = false")
})
public class JournalTransactionRequest extends EntityBase {
    public static final String FIND_ALL = "JournalTransactionRequest.findAll";

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

    @Column(name = "START_INDEX", nullable = false)
    private Long startIndex;
}