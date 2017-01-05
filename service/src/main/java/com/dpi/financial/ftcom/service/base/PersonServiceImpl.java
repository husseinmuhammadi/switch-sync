package com.dpi.financial.ftcom.service.base;

import com.dpi.financial.ftcom.api.base.PersonService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.PersonDao;
import com.dpi.financial.ftcom.model.to.Person;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(PersonService.class)
public class PersonServiceImpl extends GeneralServiceImpl<Person>
        implements PersonService {

    @EJB
    private PersonDao dao;

    @Override
    public GenericDao<Person> getGenericDao() {
        return dao;
    }
}
