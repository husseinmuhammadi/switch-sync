package com.dpi.financial.ftcom.model.base;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class EntityBase {

    protected static final String NEW_LINE = System.getProperty("line.separator");

    public EntityBase() {
    }

    public EntityBase(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_ON", columnDefinition = "Date", nullable = false)
    private Date createOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_ON", columnDefinition = "Date", nullable = false)
    private Date updateOn;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(name = "IS_DELETED", nullable = false, columnDefinition = "NUMBER(1,0) default 0")
    private Boolean deleted = Boolean.FALSE;

    @Column(name = "DESCRIPTION", length = 300)
    private String description;

    @PrePersist
    private void prePersist() {
        createOn = new Date();
        updateOn = new Date();
    }

    @PreUpdate
    private void preUpdate() {
        updateOn = new Date();
    }

    public Date getCreateOn() {
        return createOn;
    }

    public Date getUpdateOn() {
        return updateOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public void setUpdateOn(Date updateOn) {
        this.updateOn = updateOn;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
