package com.dpi.financial.ftcom.model.to.cms.card;

import com.dpi.financial.ftcom.model.base.EntityBase;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "MAGNETIC_STRIPE_SEQ")
@Table(name = "MAGNETIC_STRIPE", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"PRIMARY_ACCOUNT_NUMBER"}),
        @UniqueConstraint(columnNames = {"TRACK2"})
})
@NamedQueries({
        @NamedQuery(name = MagneticStripe.FIND_ALL, query = "select t from MagneticStripe t where t.deleted = false"),
        @NamedQuery(name = MagneticStripe.FIND_BY_PRIMARY_ACCOUNT_NUMBER, query = "select t from MagneticStripe t where t.deleted = false and t.pan = :pan")
})
public class MagneticStripe extends EntityBase {
    public static final String FIND_ALL = "MagneticStripe.findAll";
    public static final String FIND_BY_PRIMARY_ACCOUNT_NUMBER = "MagneticStripe.findByPrimaryAccountNumber";

    @Column(name = "PRIMARY_ACCOUNT_NUMBER", nullable = false, length = 19)
    private String pan;

    @Column(name = "TRACK1", nullable = true, length = 200)
    private String track1;

    @Column(name = "TRACK2", nullable = false, length = 200)
    private String track2;

    @OneToOne(mappedBy = "magneticStripe", fetch = FetchType.LAZY)
    private CardMaster cardMaster;

    public String getTrack1() {
        return track1;
    }

    public void setTrack1(String track1) {
        this.track1 = track1;
    }

    public String getTrack2() {
        return track2;
    }

    public void setTrack2(String track2) {
        this.track2 = track2;
    }

    public CardMaster getCardMaster() {
        return cardMaster;
    }

    public void setCardMaster(CardMaster cardMaster) {
        this.cardMaster = cardMaster;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }
}