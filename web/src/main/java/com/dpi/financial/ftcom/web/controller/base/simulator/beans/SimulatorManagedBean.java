package com.dpi.financial.ftcom.web.controller.base.simulator.beans;





import com.dpi.financial.ftcom.api.base.SimulatorService;
import com.dpi.financial.ftcom.model.to.Simulator;
import com.dpi.financial.ftcom.model.type.DeviceCode;
import com.dpi.financial.ftcom.model.type.ProductCode;
import com.dpi.financial.ftcom.web.controller.base.simulator.message.CreateIsoMessage;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.MessageDetails;
import com.dpi.financial.ftcom.model.type.ProcessingCode;
import org.jpos.iso.ISOMsg;


import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

@Named
@ViewScoped
public class SimulatorManagedBean implements Serializable{


    private ProcessingCode processingCode;
    private DeviceCode deviceCode;
    private ProductCode productCode;
    private String cardNumber;
    private String track2;
    private String pin;
    private String responseCode;
    private String rrn;
    private String expDate;


    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public SimulatorManagedBean() {}

    public ProcessingCode getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(ProcessingCode processingCode) {
        this.processingCode = processingCode;
    }

    public DeviceCode getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(DeviceCode deviceCode) {
        this.deviceCode = deviceCode;
    }

    public ProductCode getProductCode() {
        return productCode;
    }

    public void setProductCode(ProductCode productCode) {
        this.productCode = productCode;
    }

    public String getTrack2() {
        return track2;
    }

    public void setTrack2(String track2) {
        this.track2 = track2;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }


    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public void createSendMessage() {



        try {

            //  String SelectTypeRequest="BalanceEnquiry";

            CreateIsoMessage createIsoMessage=new CreateIsoMessage();
            MessageDetails messageDetails=new MessageDetails();


            messageDetails.setProcessingCode(getProcessingCode().getValue());
            messageDetails.setDeviceCode(getDeviceCode().getValue());
            messageDetails.setProductCode(getProductCode().getValue());
            //messageDetails.setTransactionName("BalanceEnquiry");
            messageDetails.setPan(getCardNumber());
            messageDetails.setTrack2(getTrack2());
            messageDetails.setPin(getPin());
            messageDetails.setExpDate(getExpDate());

   /*         Simulator simulator=new Simulator();
            simulator.setRrn("123");

         //   simulatorService.create(simulator);*/

            createIsoMessage.IntitIsoMsg(messageDetails);

           this.setRrn(messageDetails.getRrn());



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public  void readResponse(){


        try{



        }

        catch (Exception e)
        {
            e.printStackTrace();
        }


    }




}
