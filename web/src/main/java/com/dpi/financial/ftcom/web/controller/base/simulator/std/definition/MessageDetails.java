package com.dpi.financial.ftcom.web.controller.base.simulator.std.definition;

import org.jdom.Element;
import org.jpos.iso.ISOUtil;

public class MessageDetails {

	private String pan;
	private String pan2;
	private String pin;
	private String track2;
	private String fld3;
	private String amount;
	private String mcc;
	private String cvv2;
	private String ccy;
	private String expDate;
	private String billType="TC";
	private String billCode="000000000000000000";
	private String payCode="000000000000000000";
	private String posConditionCode;
	private byte[] pinBlock;
	private String transactionName;
	private String mti;
	private String processingCode;
	private String deviceCode;
	private String billingAmount;
	private String billingCcy;
	private String additionalData;
	private String conversionRate;
	private String ACQUIRING_INSTITUTION_IDENTIFICATION_CODE;
	private String FORWARDING_INSTITUTION_IDENTIFICATION_CODE;
	private String CARD_ACCEPTOR_TERMINAL_ID;
	private String CARD_ACCEPTOR_TERMINAL_CODE;
	private String CARD_ACCEPTOR_TERMINAL_LOCATION;
	private String productCode;

	private String rrn;

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getCARD_ACCEPTOR_TERMINAL_LOCATION() {
		return CARD_ACCEPTOR_TERMINAL_LOCATION;
	}

	public void setCARD_ACCEPTOR_TERMINAL_LOCATION(String CARD_ACCEPTOR_TERMINAL_LOCATION) {
		this.CARD_ACCEPTOR_TERMINAL_LOCATION = CARD_ACCEPTOR_TERMINAL_LOCATION;
	}

	public String getCARD_ACCEPTOR_TERMINAL_CODE() {
		return CARD_ACCEPTOR_TERMINAL_CODE;
	}

	public void setCARD_ACCEPTOR_TERMINAL_CODE(String CARD_ACCEPTOR_TERMINAL_CODE) {
		this.CARD_ACCEPTOR_TERMINAL_CODE = CARD_ACCEPTOR_TERMINAL_CODE;
	}

	public String getCARD_ACCEPTOR_TERMINAL_ID() {
		return CARD_ACCEPTOR_TERMINAL_ID;
	}

	public void setCARD_ACCEPTOR_TERMINAL_ID(String CARD_ACCEPTOR_TERMINAL_ID) {
		this.CARD_ACCEPTOR_TERMINAL_ID = CARD_ACCEPTOR_TERMINAL_ID;
	}

	public String getFORWARDING_INSTITUTION_IDENTIFICATION_CODE() {
		return FORWARDING_INSTITUTION_IDENTIFICATION_CODE;
	}

	public void setFORWARDING_INSTITUTION_IDENTIFICATION_CODE(String FORWARDING_INSTITUTION_IDENTIFICATION_CODE) {
		this.FORWARDING_INSTITUTION_IDENTIFICATION_CODE = FORWARDING_INSTITUTION_IDENTIFICATION_CODE;
	}

	public String getACQUIRING_INSTITUTION_IDENTIFICATION_CODE() {
		return ACQUIRING_INSTITUTION_IDENTIFICATION_CODE;
	}

	public void setACQUIRING_INSTITUTION_IDENTIFICATION_CODE(String ACQUIRING_INSTITUTION_IDENTIFICATION_CODE) {
		this.ACQUIRING_INSTITUTION_IDENTIFICATION_CODE = ACQUIRING_INSTITUTION_IDENTIFICATION_CODE;
	}

	public String getPointServiceEntryMode() {
		return PointServiceEntryMode;
	}

	public void setPointServiceEntryMode(String pointServiceEntryMode) {
		PointServiceEntryMode = pointServiceEntryMode;
	}

	private String PointServiceEntryMode;

	public String getConversionRate() {
		return conversionRate;
	}

	public void setConversionRate(String conversionRate) {
		this.conversionRate = conversionRate;
	}

	public String getProcessingCode() {
		return processingCode;
	}

	public void setProcessingCode(String processingCode) {
		this.processingCode = processingCode;
	}

	public MessageDetails() {
	}
	MessageDetails(Element element) {
		Element subEle = null;




	}
	
	public String getPan() {
		return pan;
	}
	
	public void setPan(String pan) {
		this.pan = pan;
	}
	
	public String getPin() {
		return pin;
	}
	
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	public String getTrack2() {
		return track2;
	}
	
	public void setTrack2(String track2) {
		this.track2 = track2;
	}
	
	public String getFld3() {
		return fld3;
	}
	
	public void setFld3(String fld3) {
		this.fld3 = fld3;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public String getCcy() {
		return ccy;
	}
	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getExpDate() {
		return expDate;
	}
	public String getPosConditionCode() {
		return posConditionCode;
	}
	public void setPosConditionCode(String posConditionCode) {
		this.posConditionCode = posConditionCode;
	}
	public void setPinBlock(byte[] pinBlock) {
		this.pinBlock = pinBlock;
	}
	public byte[] getPinBlock() {
		return pinBlock;
	}
	public void setPan2(String pan2) {
		this.pan2 = pan2;
	}
	public String getPan2() {
		return pan2;
	}
	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
	public String getCvv2() {
		return cvv2;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	public String getBillCode() {
		return billCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}
	public String getTransactionName() {
		return transactionName;
	}
	public void setMti(String mti) {
		this.mti = mti;
	}
	public String getMti() {
		return mti;
	}
	public void setBillingAmount(String billingAmount) {
		this.billingAmount = billingAmount;
	}
	public String getBillingAmount() {
		return billingAmount;
	}
	public void setBillingCcy(String billingCcy) {
		this.billingCcy = billingCcy;
	}
	public String getBillingCcy() {
		return billingCcy;
	}
	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}
	public String getAdditionalData() {
		return additionalData;
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
}
