package com.dpi.financial.ftcom.model.to;


import com.dpi.financial.ftcom.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PLAN_TYPE_SEQ")
@NamedQueries({
        @NamedQuery(name = PlanType.FIND_ALL,
                query = "select m from PlanType m")
})
public class PlanType extends BaseEntity {

    public static final String FIND_ALL = "PlanType.findAll";

    @Column(unique = true, nullable = false)
    private String key;

    @Column(length = 50, nullable = false)
    private String description;

    public PlanType() {
    }

    public PlanType(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
