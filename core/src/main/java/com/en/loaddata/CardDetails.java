package com.en.loaddata;

import loadtest.Mac;

import org.jdom.Element;
import org.jpos.iso.ISOUtil;

import com.en.pinhsm.ISO8583TesterUtil;
import com.en.pinhsm.PinUtil;

public class CardDetails {

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
	private String billingAmount;
	private String billingCcy;
	private String additionalData;

	private PinUtil putil = new PinUtil();
	CardDetails() {
	}
	CardDetails(Element element) {
		Element subEle = null;
	  if ((subEle = element.getChild(XMLConstants.ELEM_PAN)) != null) {
	      pan = subEle.getValue();
      }

	  if ((subEle = element.getChild(XMLConstants.ELEM_PAN2)) != null) {
	      setPan2(subEle.getValue());
      }
	  
      if ((subEle = element.getChild(XMLConstants.ELEM_PIN)) != null) {
    	  pin = subEle.getValue();
    	  if(pin != null && pan != null) {
    		  try{
    			  //pin = putil.createPinBlock(pan, pin);
    			  pinBlock = Mac.instance.pinEnc(pin, pan,ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getShetabPinKey()));

    		  } catch (Exception e) {
    			  e.printStackTrace();
    			  System.out.println("Pin Not Present For the Card : " + pan);
    		  }
    	  } else {
    		  System.out.println("Pin/Pan Not Present For the Card : " + pan);
    	  }
      }
      
      if ((subEle = element.getChild(XMLConstants.ELEM_TRACK2)) != null) {
    	  track2 = subEle.getValue();
	  }
      
      if ((subEle = element.getChild(XMLConstants.ELEM_FLD3)) != null) {
    	  fld3 = subEle.getValue();
      }
      
      if ((subEle = element.getChild(XMLConstants.ELEM_AMOUNT)) != null) {
    	  amount = subEle.getValue();
      }
      if ((subEle = element.getChild(XMLConstants.ELEM_MCC)) != null) {
    	  mcc = subEle.getValue();
      }

      if ((subEle = element.getChild(XMLConstants.ELEM_CVV2)) != null) {
    	  cvv2 = subEle.getValue();
      }

      if ((subEle = element.getChild(XMLConstants.ELEM_BILL_TYPE)) != null) {
    	  billType = subEle.getValue();
      }

      if ((subEle = element.getChild(XMLConstants.ELEM_BILL_CODE)) != null) {
    	  billCode = subEle.getValue();
      }

      if ((subEle = element.getChild(XMLConstants.ELEM_PAY_CODE)) != null) {
    	  payCode = subEle.getValue();
      }
      
      if ((subEle = element.getChild(XMLConstants.ELEM_CCY)) != null) {
    	  ccy = subEle.getValue();
      }
      if ((subEle = element.getChild(XMLConstants.ELEM_EXPIRY_DATE)) != null) {
    	  expDate = subEle.getValue();
      }
      if ((subEle = element.getChild(XMLConstants.ELEM_POS_CONDTION_CODE)) != null) {
    	  posConditionCode = subEle.getValue();
      }

      if ((subEle = element.getChild(XMLConstants.ELEM_TRANSACTION_NAME)) != null) {
    	  transactionName = subEle.getValue();
      }
      
	  if ((subEle = element.getChild(XMLConstants.ELEM_MTI)) != null) {
	      mti = subEle.getValue();
      }
	  if ((subEle = element.getChild(XMLConstants.ELEM_BILLING_AMOUNT)) != null) {
		  billingAmount = subEle.getValue();
      }
	  if ((subEle = element.getChild(XMLConstants.ELEM_BILLING_CCY)) != null) {
		  billingCcy = subEle.getValue();
      }
	  if ((subEle = element.getChild(XMLConstants.ELEM_ADDITIONAL_PRIVATE_DATA)) != null) {
		  additionalData = subEle.getValue();
      }
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
	public PinUtil getPutil() {
		return putil;
	}
	public void setPutil(PinUtil putil) {
		this.putil = putil;
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
}
