package com.dpi.financial.ftcom.model.to.cms.card;

import com.dpi.financial.ftcom.model.base.EntityBase;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by h.mohammadi on 10/23/2016.
 */
@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "CARD_MASTER_SEQ")
@Table(name = "CARD_MASTER", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"PRIMARY_ACCOUNT_NUMBER"})
})
@NamedQueries({
        @NamedQuery(name = CardMaster.FIND_ALL, query = "select t from CardMaster t where t.deleted = false")
})
public class CardMaster extends EntityBase {
    public static final String FIND_ALL = "CardMaster.findAll";

    @Column(name = "PRIMARY_ACCOUNT_NUMBER", nullable = false, length = 19)
    private String pan;

    @Column(name = "NAME", nullable = true, length = 26)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "EXPIRATION_DATE", columnDefinition = "Date", nullable = false)
    private Date expirationDate;

    @Column(name = "SERVICE_CODE", nullable = true, length = 3)
    private String serviceCode;

    // CVV2
    @Column(name = "CARD_VERIFICATION_VALUE", nullable = true, length = 3)
    private String cvv;

    @Column(name = "DISCRETIONARY_DATA", nullable = true, length = 4)
    private String discretionaryData;

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getDiscretionaryData() {
        return discretionaryData;
    }

    public void setDiscretionaryData(String discretionaryData) {
        this.discretionaryData = discretionaryData;
    }
}
