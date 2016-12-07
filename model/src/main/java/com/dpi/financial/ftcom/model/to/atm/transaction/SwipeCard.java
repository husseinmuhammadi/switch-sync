package com.dpi.financial.ftcom.model.to.atm.transaction;

import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.converter.OperationStateConverter;
import com.dpi.financial.ftcom.model.converter.ProcessingCodeConverter;
import com.dpi.financial.ftcom.model.to.atm.JournalFile;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.type.OperationState;

import javax.persistence.*;
import java.util.Date;

/**
 * JournalFile entity persist physical journal file information on database
 * @since ver 1.0.0 modified by Hossein Mohammadi w.r.t Issue #1 as on Monday, December 05, 2016
 *  <li>Prepare ATM swipe card based on journal content</li>
 */
@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "ATM_SWIPE_CARD_SEQ")
@Table(name = "ATM_SWIPE_CARD")
@NamedQueries({
        @NamedQuery(name = SwipeCard.FIND_ALL, query = "select t from SwipeCard t where t.deleted = false")
})
public class SwipeCard extends EntityBase {
    public static final String FIND_ALL = "SwipeCard.findAll";

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TERMINAL_ID", nullable = false)
    private Terminal terminal;
    */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOURNAL_FILE_ID", nullable = false)
    private JournalFile journalFile;

    // Logical Unit Number
    @Column(name = "LUNO", nullable = false, length = 10)
    private String luno;

    @Column(name = "TRACK_1_DATA", length = 100)
    private String track1Data;

    @Column(name = "TRACK_2_DATA", length = 100)
    private String track2Data;

    @Column(name = "TRACK_3_DATA", length = 150)
    private String track3Data;

    @Column(name = "PRIMARY_ACCOUNT_NUMBER", length = 19)
    private String pan;

    @Temporal(TemporalType.DATE)
    @Column(name = "SWIPE_DATE", nullable = false)
    private Date swipeDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SWIPE_TIME", nullable = false)
    private Date swipeTime;

    @Column(name = "FILE_NAME", nullable = false, length = 12)
    private String fileName;

    @Column(name = "LINE_START", nullable = false)
    private Long lineStart;

    @Column(name = "LINE_END", nullable = false)
    private Long lineEnd;

    @Column(name = "OPERATION_STATE", nullable = false, length = 1)
    @Convert(converter = OperationStateConverter.class)
    private OperationState operationState;

    /*
    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }
    */

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

    public String getTrack1Data() {
        return track1Data;
    }

    public void setTrack1Data(String track1Data) {
        this.track1Data = track1Data;
    }

    public String getTrack2Data() {
        return track2Data;
    }

    public void setTrack2Data(String track2Data) {
        this.track2Data = track2Data;
    }

    public String getTrack3Data() {
        return track3Data;
    }

    public void setTrack3Data(String track3Data) {
        this.track3Data = track3Data;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public Date getSwipeDate() {
        return swipeDate;
    }

    public void setSwipeDate(Date swipeDate) {
        this.swipeDate = swipeDate;
    }

    public Date getSwipeTime() {
        return swipeTime;
    }

    public void setSwipeTime(Date swipeTime) {
        this.swipeTime = swipeTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getLineStart() {
        return lineStart;
    }

    public void setLineStart(Long lineStart) {
        this.lineStart = lineStart;
    }

    public Long getLineEnd() {
        return lineEnd;
    }

    public void setLineEnd(Long lineEnd) {
        this.lineEnd = lineEnd;
    }

    public OperationState getOperationState() {
        return operationState;
    }

    public void setOperationState(OperationState operationState) {
        this.operationState = operationState;
    }
}