package com.dpi.financial.ftcom.web.controller.base.isc.transaction.network;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.isc.transaction.network.CutoverRequestService;
import com.dpi.financial.ftcom.model.to.isc.transaction.network.CutoverRequest;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CutoverRequestManager extends ControllerManagerBase<CutoverRequest> implements Serializable {
    private static final long serialVersionUID = 6457436395544349233L;

    @EJB
    private CutoverRequestService service;

    // private List<CutoverRequest> cutoverRequests;

    public CutoverRequestManager() {
        super(CutoverRequest.class);
    }

    @Override
    public GeneralServiceApi<CutoverRequest> getGeneralServiceApi() {
        return service;
    }

    public void onLoad() {
    }

    public List<CutoverRequest> getCutoverRequests() {
        return entityList;
    }

    public void setCutoverRequests(List<CutoverRequest> cutoverRequests) {
        this.entityList = cutoverRequests;
    }
}
