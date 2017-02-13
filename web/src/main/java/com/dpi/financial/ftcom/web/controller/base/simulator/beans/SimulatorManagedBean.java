package com.dpi.financial.ftcom.web.controller.base.simulator.beans;





import com.dpi.financial.ftcom.web.controller.base.simulator.message.CreateIsoMessage;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.MessageDetails;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class SimulatorManagedBean implements Serializable{




    private String processingCode;
    private String deviceCode;
    private String productCode;
    private String cardNumber;
    private String track2;
    private String pin;
    private String responseCode;

    public SimulatorManagedBean() {
    }

    public String getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
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

    public void createSendMessage() {



        try {

            //  String SelectTypeRequest="BalanceEnquiry";

            CreateIsoMessage createIsoMessage=new CreateIsoMessage();
            MessageDetails messageDetails=new MessageDetails();


            messageDetails.setProcessingCode(getProcessingCode());
            messageDetails.setDeviceCode(getDeviceCode());
            messageDetails.setProductCode(getProductCode());
            //messageDetails.setTransactionName("BalanceEnquiry");
            messageDetails.setPan(getCardNumber());
            messageDetails.setTrack2(getTrack2());
            messageDetails.setPin(getPin());
            createIsoMessage.IntitIsoMsg(messageDetails);


            System.out.println("salam");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }




}
