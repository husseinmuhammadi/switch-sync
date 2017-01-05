package com.dpi.financial.ftcom.model.to;

import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.converter.*;
import com.dpi.financial.ftcom.model.type.*;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PERSON_SEQ")
@Table(name = "PERSON")
@NamedQueries({
        @NamedQuery(name = Person.FIND_ALL, query = "select t from Person t where t.deleted = false")
})
public class Person extends EntityBase {

    public static final String FIND_ALL = "Person.findAll";

    @Column(name = "FIRST_NAME", length = 200)
    private String firstName;

    @Column(name = "LAST_NAME", length = 200)
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}