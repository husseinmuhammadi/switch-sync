package com.dpi.financial.ftcom.web.controller.base.common;

import com.dpi.financial.ftcom.api.base.atm.TerminalService;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.type.*;
import com.dpi.financial.ftcom.model.type.atm.ndc.Language;
import com.dpi.financial.ftcom.model.type.isc.DeviceCode;
import com.dpi.financial.ftcom.model.type.isc.transaction.InteractionPoint;
import com.dpi.financial.ftcom.web.controller.base.AbstractController;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */

@Named
@SessionScoped
public class SelectItemsController extends AbstractController implements Serializable {

    @EJB
    TerminalService terminalService;

    public Map<String, String> getInteractionPointItems() {
        Map<String, String> items = new HashMap<String, String>();
        items.put("", getLabel("label.select.empty"));
        for (InteractionPoint interactionPoint : InteractionPoint.values()) {
            items.put(interactionPoint.name(), getLabel(interactionPoint));
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
        for (ProcessingCode processingCode : Arrays.stream(ProcessingCode.values())
                .filter(p -> !p.isNdc()).collect(Collectors.toList())) {
            items.put(processingCode.name(), getLabel(processingCode.getFullName()));
        }
        return items;
    }

    public Map<String, String> getNdcOperationCodeLanguageItems() {
        Map<String, String> items = new HashMap<String, String>();
        items.put("", getLabel("label.select.empty"));
        for (Language language : Language.values()) {
            items.put(language.name(), getLabel(language.getFullName()));
        }
        return items;
    }

    public Map<String, String> getYesNoTypeItems() {
        Map<String, String> items = new HashMap<String, String>();
        items.put("", getLabel("label.select.empty"));
        for (YesNoType type : YesNoType.values()) {
            items.put(type.name(), getLabel(type.getFullName()));
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
