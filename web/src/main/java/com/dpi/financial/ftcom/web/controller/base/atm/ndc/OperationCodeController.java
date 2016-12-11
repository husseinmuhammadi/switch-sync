package com.dpi.financial.ftcom.web.controller.base.atm.ndc;


import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.atm.ndc.OperationCodeService;
import com.dpi.financial.ftcom.model.to.atm.ndc.OperationCode;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class OperationCodeController extends ControllerBase<OperationCode> implements Serializable {

    @EJB
    private OperationCodeService service;

    public OperationCodeController() {
        super(OperationCode.class);
    }

    public OperationCode getOperationCode() {
        return super.getEntity();
    }

    public void setOperationCode(OperationCode operationCode) {
        super.setEntity(operationCode);
    }

    @Override
    public GeneralServiceApi<OperationCode> getGeneralServiceApi() {
        return service;
    }

    @Override
    public void init() {

    }

    @Override
    public void afterLoad() {

    }
}
