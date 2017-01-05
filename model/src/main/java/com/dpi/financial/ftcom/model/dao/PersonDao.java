package com.dpi.financial.ftcom.model.dao;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.Person;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class PersonDao extends GenericDao<Person> {
    public PersonDao() {
        super(Person.class);
    }

    public List<Person> findAll() {
        return createNamedQuery(Person.FIND_ALL).getResultList();
    }
}
