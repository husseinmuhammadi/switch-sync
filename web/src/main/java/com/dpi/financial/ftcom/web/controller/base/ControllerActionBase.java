package com.dpi.financial.ftcom.web.controller.base;

/**
 * Created by h.mohammadi on 8/30/2016.
 */
public interface ControllerActionBase {

    void prepare();

    boolean validate();

    String create();

    String update();

    String delete();

    String delete(Long id);

    String refresh();

    void afterLoad();
}
