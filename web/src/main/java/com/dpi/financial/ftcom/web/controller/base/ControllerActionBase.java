package com.dpi.financial.ftcom.web.controller.base;

/**
 *
 */
public interface ControllerActionBase {

    /**
     *
     */
    void prepare();

    /**
     *
     */
    boolean validate();

    /**
     *
     */
    String create();

    /**
     *
     */
    String update();

    /**
     *
     */
    String delete();

    /**
     *
     */
    String delete(Long id);

    /**
     *
     */
    String refresh();

    /**
     *
     */
    void afterLoad();
}
