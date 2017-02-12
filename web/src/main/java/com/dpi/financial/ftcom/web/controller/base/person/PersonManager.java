package com.dpi.financial.ftcom.web.controller.base.person;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.PersonService;
import com.dpi.financial.ftcom.model.to.Person;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class PersonManager extends ControllerManagerBase<Person> implements Serializable {

    @EJB
    private PersonService personService;

    public PersonManager() {
        super(Person.class);
    }

    private List<Person> personList;

    @Override
    public GeneralServiceApi<Person> getGeneralServiceApi() {
        return personService;
    }

    @Override
    protected void onLoad() {

    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
