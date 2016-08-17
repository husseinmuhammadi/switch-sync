package ir.team.insurance.complementary.model.base;

import org.apache.commons.lang3.ArrayUtils;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {

    public BaseEntity() {
    }

    public BaseEntity(Long id) {
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

    public void resetValues() {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            Field[] supperFields = this.getClass().getSuperclass().getDeclaredFields();
            Field[] allFields = ArrayUtils.addAll(fields, supperFields);
            Object o = this.getClass().newInstance();

            for (Field field : allFields) {
                String name = field.getName();
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) || name.startsWith("_")) {
                    continue;
                }
                Class<?> type = field.getType();


                Method setterMethod = this.getClass().getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), type);
                Method getterMethod;
                if (!type.equals(Boolean.class)) {
                    getterMethod = this.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
                } else {
                    getterMethod = this.getClass().getMethod("is" + name.substring(0, 1).toUpperCase() + name.substring(1));
                }
                setterMethod.invoke(this, getterMethod.invoke(o));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
