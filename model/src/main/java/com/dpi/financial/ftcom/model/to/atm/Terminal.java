package com.dpi.financial.ftcom.model.to.atm;

import com.dpi.financial.ftcom.model.base.EntityBase;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "ATM_TERMINAL_SEQ")
@Table(name = "ATM_TERMINAL_MASTER", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"TERMINAL_ID"}),
        @UniqueConstraint(columnNames = {"LUNO"})
})
@NamedQueries({
        @NamedQuery(name = Terminal.FIND_ALL, query = "select t from Terminal t where t.deleted = false order by t.luno"),
        @NamedQuery(name = Terminal.FIND_BY_LUNO, query = "select t from Terminal t where t.deleted = false and t.luno = :luno")
})

public class Terminal extends EntityBase {

    public static final String FIND_ALL = "Terminal.findAll";
    public static final String FIND_BY_LUNO = "Terminal.findByLuno";

    @Column(name = "TERMINAL_ID", nullable = false, length = 10)
    private String terminalId;

    // Logical Unit Number
    @Column(name = "LUNO", nullable = false, length = 10)
    private String luno;

    @Column(name = "TERMINAL_IP", nullable = true, length = 16)
    private String terminalIp;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getLuno() {
        return luno;
    }

    public void setLuno(String luno) {
        this.luno = luno;
    }

    public String getTerminalIp() {
        return terminalIp;
    }

    public void setTerminalIp(String terminalIp) {
        this.terminalIp = terminalIp;
    }
}




/*
        , DES_KEY VARCHAR2(50 BYTE)
        , ECHO_INTERVAL NUMBER
        , KEY_EXCHANGE_INTERVAL NUMBER
        , PIN_ENABLED_STATUS VARCHAR2(5 BYTE)
        , RESPONSE_TIMEOUT NUMBER
        , REVERSAL_RETRY_COUNT NUMBER
        , BANK_CODE VARCHAR2(6 BYTE)
        , BRANCH_CODE VARCHAR2(6 BYTE)
        , CITY VARCHAR2(20 BYTE)
        , LOCATION VARCHAR2(30 BYTE)
        , KCV VARCHAR2(50 BYTE)
        , BIN VARCHAR2(50 BYTE)
        , ADDRESS1 VARCHAR2(50 BYTE)
        , ADDRESS2 VARCHAR2(50 BYTE)
        , ADDRESS3 VARCHAR2(50 BYTE)
        , TOWN_CITY VARCHAR2(20 BYTE)
        , STATE VARCHAR2(20 BYTE)
        , COUNTRY VARCHAR2(10 BYTE)
        , PIN VARCHAR2(6 BYTE)
        , CONTACT_PERSON VARCHAR2(20 BYTE)
        , CONTACT_EMAIL VARCHAR2(200 BYTE)
        , CARD_READER_STATUS VARCHAR2(1 BYTE)
        , CASH_LOW_STATUS VARCHAR2(1 BYTE)
        , CASS_1_DENOM VARCHAR2(8 BYTE)
        , CASS_1_ENABLED VARCHAR2(1 BYTE)
        , CASS_1_NOTES NUMBER
        , CASS_1_STATUS VARCHAR2(1 BYTE)
        , CASS_2_DENOM VARCHAR2(8 BYTE)
        , CASS_2_ENABLED VARCHAR2(1 BYTE)
        , CASS_2_NOTES NUMBER
        , CASS_2_STATUS VARCHAR2(1 BYTE)
        , CASS_3_DENOM VARCHAR2(8 BYTE)
        , CASS_3_ENABLED VARCHAR2(1 BYTE)
        , CASS_3_NOTES NUMBER
        , CASS_3_STATUS VARCHAR2(1 BYTE)
        , CASS_4_DENOM VARCHAR2(10 BYTE)
        , CASS_4_ENABLED VARCHAR2(1 BYTE)
        , CASS_4_NOTES NUMBER
        , CASS_4_STATUS VARCHAR2(1 BYTE)
        , DISPENSER_STATUS VARCHAR2(1 BYTE)
        , DOOR_STATUS VARCHAR2(1 BYTE)
        , ECHO_COUNT NUMBER
        , ERR VARCHAR2(1000 BYTE)
        , FP_DEVICE_STATUS VARCHAR2(1 BYTE)
        , JOURNAL_SPACE_STATUS VARCHAR2(1 BYTE)
        , LOGON_STATUS VARCHAR2(1 BYTE)
        , POWER_STATUS VARCHAR2(1 BYTE)
        , PRINTER_STATUS VARCHAR2(1 BYTE)
        , DISPENSING_LOGIC VARCHAR2(25 BYTE)
        , SOFTWARE_ID VARCHAR2(10 BYTE)
        , CONFIGURATION_ID VARCHAR2(4 BYTE)
        , PRODUCT_ID VARCHAR2(2 BYTE)
        , TOD_CLOCK_STATUS VARCHAR2(1 BYTE)
        , TERMINAL_DATE_TIME DATE
        , CASH_LOW_PARA_CASS1 NUMBER
        , CASH_LOW_PARA_CASS2 NUMBER
        , CASH_LOW_PARA_CASS3 NUMBER
        , CASH_LOW_PARA_CASS4 NUMBER
        , REJECTED_NOTE_COUNT VARCHAR2(30 BYTE)
        , DISPENSED_NOTE_COUNT VARCHAR2(30 BYTE)
        , MODIFIED_DATE_TIME DATE
        , LAST_CDM_ERROR DATE

*/