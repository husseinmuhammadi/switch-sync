package com.dpi.financial.ftcom.model.to;

import com.dpi.financial.ftcom.model.base.EntityBase;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "SimulatorTransaction_SEQ")
@Table(name = "LMO_CUST_TRANSACTION_DTLS")
@NamedQueries({
        @NamedQuery(name = Simulator.FIND_ALL, query = "select t from Simulator t where t.deleted = false"),
        @NamedQuery(name = Simulator.FIND_BY_RRN, query = "select t from Simulator t where t.deleted = false and t.rrn = :rrn")
})
public class Simulator extends EntityBase {

    public static final String FIND_ALL = "Simulator.findAll";
    public static final String FIND_BY_RRN = "Simulator.findByRRN";

    @Column(name = "RRN", length = 200)
    private String rrn;

    @Column(name = "RESPONSE_CODE", length = 200)
    private String responseCode;

    @Column(name = "PROCESS_CODE", length = 200)
    private String processingCode;

    @Column(name = "PIN", length = 200)
    private String pin;

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    /*private String deviceCode;
    private String productCode;
    private String cardNumber;
    private String track2;*/


}