package com.dpi.financial.ftcom.web.controller.base.isc.transaction;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.isc.transaction.SwitchTransactionService;
import com.dpi.financial.ftcom.model.to.isc.transaction.FinancialBase;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class SwitchTransactionManager extends ControllerManagerBase<FinancialBase> implements Serializable {
    private static final long serialVersionUID = 6457436395544349233L;

    @EJB
    private SwitchTransactionService service;

    // private List<SwitchTransaction> switchTransactions;

    public SwitchTransactionManager() {
        super(FinancialBase.class);
    }

    @Override
    public GeneralServiceApi<FinancialBase> getGeneralServiceApi() {
        return service;
    }

    public void onLoad() {
    }

    public List<FinancialBase> getSwitchTransactions() {
        return entityList;
    }

    public void setSwitchTransactions(List<FinancialBase> switchTransactions) {
        this.entityList = switchTransactions;
    }
}
