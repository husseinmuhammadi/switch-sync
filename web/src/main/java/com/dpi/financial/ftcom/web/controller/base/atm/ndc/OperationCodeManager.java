package com.dpi.financial.ftcom.web.controller.base.atm.ndc;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.atm.ndc.OperationCodeService;
import com.dpi.financial.ftcom.model.to.atm.ndc.OperationCode;
import com.dpi.financial.ftcom.model.type.ProcessingCode;
import com.dpi.financial.ftcom.model.type.YesNoType;
import com.dpi.financial.ftcom.model.type.atm.ndc.Language;
import com.dpi.financial.ftcom.utility.convert.Convert;
import com.dpi.financial.ftcom.utility.date.DateUtil;
import com.dpi.financial.ftcom.utility.regex.RegexConstant;
import com.dpi.financial.ftcom.utility.xml.XmlHelper;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class OperationCodeManager extends ControllerManagerBase<OperationCode> implements Serializable {

    @EJB
    private OperationCodeService service;

    // private List<OperationCode> operationCodeList;

    public OperationCodeManager() {
        super(OperationCode.class);
    }

    @Override
    public GeneralServiceApi<OperationCode> getGeneralServiceApi() {
        return service;
    }

    public void onLoad() {
        entityList = service.findAllByEffectiveDate(DateUtil.getCurrentDate());
    }

    public void loadFromXmlFile(AjaxBehaviorEvent event) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        List<OperationCode> operationCodes = new ArrayList<OperationCode>();

        try {
            builder = factory.newDocumentBuilder();
            // Document document = builder.parse(new FileInputStream("src/main/resources/ndc_iso_transcode_mapping.xml"));
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Document document = builder.parse(classLoader.getResourceAsStream("ndc_iso_transcode_mapping.xml"));
            NodeList nodeList = document.getDocumentElement().getElementsByTagName(RegexConstant.ATM_PROP_OPERATION_CODE);
            for (int index = 0; index < nodeList.getLength(); index++) {
                Element element = (Element) nodeList.item(index);
                OperationCode operationCode = new OperationCode();
                Element parent = (Element) element.getParentNode().getParentNode();

                operationCode.setId(1L);

                operationCode.setProcessingCode(ProcessingCode.getInstance(XmlHelper.getUniqueChildContent(parent, RegexConstant.ATM_PROP_PROCESSING_CODE)));
                operationCode.setTransactionType(XmlHelper.getUniqueChildContent(parent, RegexConstant.ATM_PROP_TRANSACTION_TYPE));
                operationCode.setTransactionDescription(XmlHelper.getUniqueChildContent(parent, RegexConstant.ATM_PROP_TRANSACTION_DESCRIPTION));

                operationCode.setOperationCodeBuffer(XmlHelper.getUniqueChildContent(element, RegexConstant.ATM_PROP_OPERATION_CODE_BUFFER));
                operationCode.setFromAccount(XmlHelper.getUniqueChildContent(element, RegexConstant.ATM_PROP_FROM_ACCOUNT));
                operationCode.setToAccount(XmlHelper.getUniqueChildContent(element, RegexConstant.ATM_PROP_TO_ACCOUNT));
                operationCode.setLanguage(Language.getInstance(XmlHelper.getUniqueChildContent(element, RegexConstant.ATM_PROP_LANG)));
                String amount = XmlHelper.getUniqueChildContent(element, RegexConstant.ATM_PROP_AMOUNT);
                if (amount != null && !amount.isEmpty())
                    operationCode.setAmount(Convert.parseDecimal(amount));

                String id = XmlHelper.getUniqueChildContent(parent, RegexConstant.ATM_PROP_ID);
                // operationCode.setId();
                String name = XmlHelper.getUniqueChildContent(parent, RegexConstant.ATM_PROP_NAME);
                //operationCode.setName();

                operationCode.setPrintReceipt(YesNoType.getInstance(XmlHelper.getUniqueChildContent(element, RegexConstant.ATM_PROP_PRINT_RECEIPT)));
                operationCode.setOtherAccount(YesNoType.getInstance(XmlHelper.getUniqueChildContent(element, RegexConstant.ATM_PROP_OTHER_ACCOUNT)));

                String selectedAccountNo = XmlHelper.getUniqueChildContent(element, RegexConstant.ATM_PROP_SELECTED_ACCOUNT_NO);
                if (!StringUtils.isEmpty(selectedAccountNo))
                    operationCode.setSelectedAccountNo(Long.parseLong(selectedAccountNo));

                operationCode.setEffectiveDate(DateUtil.getCurrentDate());
                operationCode.setEffectiveTime(DateUtil.getCurrentTime());

                operationCodes.add(operationCode);
            }
            service.createBatch(operationCodes);
            entityList = service.findAllByEffectiveDate(DateUtil.getCurrentDate());
        } catch (ParserConfigurationException | SAXException | IOException | ParseException e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }

    public List<OperationCode> getOperationCodeList() {
        return entityList;
    }

    public void setOperationCodeList(List<OperationCode> operationCodeList) {
        this.entityList = operationCodeList;
    }
}
