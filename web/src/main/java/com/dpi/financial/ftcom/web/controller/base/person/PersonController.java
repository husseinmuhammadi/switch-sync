package com.dpi.financial.ftcom.web.controller.base.person;


import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.PersonService;
import com.dpi.financial.ftcom.model.to.Person;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class PersonController extends ControllerBase<Person>
        implements Serializable {

    @EJB
    private PersonService personService;

    public PersonController() {
        super(Person.class);
    }

    public Person getPerson() {
        return super.getEntity();
    }

    public void setPerson(Person person) {
        super.setEntity(person);
    }

    @Override
    public GeneralServiceApi<Person> getGeneralServiceApi() {
        return personService;
    }

    @Override
    public void init() {

    }

    @Override
    public void afterLoad() {

    }
}
