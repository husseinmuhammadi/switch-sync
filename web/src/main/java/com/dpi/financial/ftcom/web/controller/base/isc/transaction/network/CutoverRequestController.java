package com.dpi.financial.ftcom.web.controller.base.isc.transaction.network;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.isc.transaction.network.CutoverRequestService;
import com.dpi.financial.ftcom.model.to.isc.transaction.network.CutoverRequest;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class CutoverRequestController extends ControllerBase<CutoverRequest> implements Serializable {

    private static final long serialVersionUID = -5308684233911832396L;

    @EJB
    private CutoverRequestService service;

    // private CutoverRequest cutoverRequest;

    public CutoverRequestController() {
        super(CutoverRequest.class);
    }

    public CutoverRequest getCutoverRequest() {
        return getEntity();
    }

    public void setCutoverRequest(CutoverRequest cutoverRequest) {
        setEntity(cutoverRequest);
    }

    @Override
    public GeneralServiceApi<CutoverRequest> getGeneralServiceApi() {
        return service;
    }

    @Override
    public void init() {

    }

    @Override
    public void afterLoad() {

    }

    @Override
    public void prepare() {
        super.prepare();
        entity.setMti("0800");
    }
}
