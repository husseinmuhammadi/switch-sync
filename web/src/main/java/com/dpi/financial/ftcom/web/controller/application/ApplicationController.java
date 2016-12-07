package com.dpi.financial.ftcom.web.controller.application;

import com.dpi.financial.ftcom.web.annotation.Eager;
import com.dpi.financial.ftcom.web.controller.base.AbstractController;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

/**
 * Created by h.mohammadi on 11/19/2016.
 * http://stackoverflow.com/questions/38412301/what-is-the-equivalent-of-managedbeaneager-true-in-cdi
 */
@Named
@Eager
@ApplicationScoped
public class ApplicationController extends AbstractController {
    @PostConstruct
    public void init() {
        System.out.println("Application scoped init!");
    }
}