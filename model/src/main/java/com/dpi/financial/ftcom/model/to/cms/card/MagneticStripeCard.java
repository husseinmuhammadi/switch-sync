package com.dpi.financial.ftcom.model.to.cms.card;

import com.dpi.financial.ftcom.model.base.EntityBase;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "MAGNETIC_STRIPE_CARD_SEQ")
@Table(name = "MAGNETIC_STRIPE_CARD", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"TRACK2"})
})
@NamedQueries({
        @NamedQuery(name = MagneticStripeCard.FIND_ALL, query = "select t from MagneticStripeCard t where t.deleted = false")
})
public class MagneticStripeCard extends EntityBase {
    public static final String FIND_ALL = "MagneticStripeCard.findAll";

    @Column(name = "TRACK1", nullable = true, length = 200)
    private String track1;

    @Column(name = "TRACK2", nullable = false, length = 200)
    private String track2;

    @OneToOne
    @JoinColumn(name = "CARD_MASTER_ID")
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
}