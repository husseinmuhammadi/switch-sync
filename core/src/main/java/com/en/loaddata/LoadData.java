package com.en.loaddata;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;


/**
 * @author Souraf
 *
 */

public class LoadData {
	   private static Logger logger = Logger.getLogger(LoadData.class);
	   public LoadData() {
		   System.out.println("calling load data");
	     PropertyConfigurator.configure("log4j.properties");
	     staticLoad();
	   }
	   
	   public static void staticLoad() {
		   logger.info("Start to load XML");
		   loadXml();
		   logger.info("Load XML sucessfully");
	   }

	   private static CardDetails[] cardDetails_;

	   public static void loadXml() {
		   
	      String xmlFilePath = "./cfg/LoadData.xml";
	      System.out.println("loading xml : " + xmlFilePath);
	      File xml = new File(xmlFilePath);
	      if (!xml.exists()) {
	    	  System.out.println("File not exitst ");
	         logger.info("File not found :" + xmlFilePath);
	      }
	      loadXml(xml);
	   }

	   public static void loadXml(File xmlFile) {
	      Element root = fetchRootElement(xmlFile);
	      setCardDetails(root);
	      logger.info("XML Load Successfully");
	   }

	   private static Element fetchRootElement(File xmlFile) {
	      SAXBuilder builder = new SAXBuilder();
	      Element root = null;
	      try {
	         Document document = builder.build(xmlFile);
	         root = document.getRootElement();
	      } catch (JDOMException je) {
	         logger.error(je.getMessage());
	      } catch (IOException ie) {
	         logger.info("IO Exception :" + xmlFile);
	         logger.error(ie.getMessage());
	      }
	      return root;
	   }

	   private static void setCardDetails(Element root) {
	      List lstCardDetails = root.getChildren(XMLConstants.ELEM_CARD_DETAILS);
	      CardDetails[] cardDetails = new CardDetails[lstCardDetails.size()];
	      Iterator iterator = lstCardDetails.iterator();
	      int index = 0;
	      while (iterator.hasNext()) {
	         Element dtls = (Element)iterator.next();
	         cardDetails[index] = new CardDetails(dtls);
	         index++;
	         logger.info("Card [" + index + "] Successfully");
	      }
	      cardDetails_ = cardDetails;
	   }
	   
	   public static CardDetails[] getCardDetails() {
		   return cardDetails_;
	   }
	   
	   public static void cleanDetails(){
		   cardDetails_ = null;
	   }
}
