package com.dpi.financial.ftcom.web.controller.base.common;

import com.dpi.financial.ftcom.api.base.atm.TerminalService;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.type.*;
import com.dpi.financial.ftcom.web.controller.base.AbstractController;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */

@Named
@SessionScoped
public class SelectItemsController extends AbstractController implements Serializable {

    @EJB
    TerminalService terminalService;

    public Map<String, String> getFinancialServiceProviderItems() {
        Map<String, String> items = new HashMap<String, String>();
        items.put("", getLabel("label.select.empty"));
        for (FinancialServiceProvider financialServiceProvider : FinancialServiceProvider.values()) {
            items.put(financialServiceProvider.name(), getLabel(financialServiceProvider));
        }
        return items;
    }

    public Map<String, String> getTestConditionTypeItems() {
        Map<String, String> items = new HashMap<String, String>();
        items.put("", getLabel("label.select.empty"));
        for (TestConditionType testConditionType : TestConditionType.values()) {
            items.put(testConditionType.name(), getLabel(testConditionType));
        }
        return items;
    }

    public Map<String, String> getProcessingCodeItems() {
        Map<String, String> items = new HashMap<String, String>();
        items.put("", getLabel("label.select.empty"));
        for (ProcessingCode processingCode : ProcessingCode.values()) {
            items.put(processingCode.name(), getLabel(processingCode.getFullName()));
        }
        return items;
    }

    public Map<String, String> getDeviceCodeItems() {
        Map<String, String> items = new HashMap<String, String>();
        items.put("", getLabel("label.select.empty"));
        for (DeviceCode deviceCode : DeviceCode.values()) {
            items.put(deviceCode.name(), getLabel(deviceCode.getFullName()));
        }
        return items;
    }

    public Map<String, String> getProductCodeItems() {
        Map<String, String> items = new HashMap<String, String>();
        items.put("", getLabel("label.select.empty"));
        for (ProductCode productCode : ProductCode.values()) {
            items.put(productCode.name(), getLabel(productCode.getFullName()));
        }
        return items;
    }

    public Map<String, String> getTransactionModeItems() {
        Map<String, String> items = new HashMap<String, String>();
        items.put("", getLabel("label.select.empty"));
        for (TransactionMode transactionMode : TransactionMode.values()) {
            items.put(transactionMode.name(), getLabel(transactionMode.getFullName()));
        }
        return items;
    }

    public Map<String, String> getAtmTerminalItems() {
        Map<String, String> items = new HashMap<String, String>();
        items.put("", getLabel("label.select.empty"));
        List<Terminal> terminals = terminalService.findAll();
        for (Terminal terminal : terminals) {
            items.put(terminal.getLuno(), terminal.getLuno());
        }
        return items;
    }
}
