package com.dpi.financial.ftcom.model.base;

import com.dpi.financial.ftcom.utility.i18n.MessageUtil;
import com.dpi.financial.ftcom.utility.regex.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;

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
    @Column(name = "CREATE_ON", columnDefinition = "Date default sysdate", nullable = false)
    private Date createOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_ON", columnDefinition = "Date default sysdate", nullable = false)
    private Date updateOn;

    @Version
    @Column(nullable = false, columnDefinition = "NUMBER(19,0) default 0")
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

    public List<String> getFieldNames() {
        List<String> fieldNames = new ArrayList<String>();

        for (Field field : this.getClass().getDeclaredFields()) {
            Class type = field.getType();
            String name = field.getName();
            Annotation[] annotations = field.getDeclaredAnnotations();

            if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) || name.startsWith("_")) {
                continue;
            }

            fieldNames.add(name);
        }

        return fieldNames;
    }

    public String getFieldName(String fieldName, ResourceBundle resourceBundle) {
        String className = Character.toLowerCase(this.getClass().getSimpleName().charAt(0)) + this.getClass().getSimpleName().substring(1);
        String name = "label" + "." + className + "." + fieldName;
        return MessageUtil.getMessage(name, resourceBundle);
    }

    public Object getFieldValue(String fieldName, ResourceBundle labelBundle) {
        Object value = null;

        System.out.println(
                MessageFormat.format("Get {0}.{1}", this.getClass().getName(), fieldName)
        );

        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                if (!name.equals(fieldName)) {
                    continue;
                }

                Class<?> type = field.getType();

                Method setterMethod = null;
                setterMethod = this.getClass().getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), type);
                Method getterMethod;
                if (!type.equals(Boolean.class)) {
                    getterMethod = this.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
                } else {
                    getterMethod = this.getClass().getMethod("is" + name.substring(0, 1).toUpperCase() + name.substring(1));
                }
                value = getterMethod.invoke(this);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
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
