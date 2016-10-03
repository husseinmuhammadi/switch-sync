package com.en.listener;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;

import com.en.hsm.FSDMsg;
import com.en.hsm.ThalesCommands;

public class VisaListener implements ISORequestListener,
	LogSource, Configurable {

   private Logger logger_;

   private String realm_;

   protected Space space_;

   protected String queue;

   protected long respTime;

   private Configuration configuration_;

   public Logger getLogger() {
      return this.logger_;
   }

   public String getRealm() {
      return this.realm_;
   }

   public void setLogger(Logger logger, String realm) {
      this.logger_ = logger;
      this.realm_ = realm;
   }


   public void setConfiguration(Configuration configuration)
      throws ConfigurationException {
		  System.out.println("Configuration");
      this.configuration_ = configuration;
      queue = configuration.get("queue", null);
      respTime = Long.valueOf(configuration.get("respTime", null));
      if (queue == null) {
         throw new ConfigurationException("Property not specified : queue");
      }
      space_ = SpaceFactory.getSpace(configuration.get("space", null));
   }

   public boolean process(ISOSource isoSource, ISOMsg isoMsg) {
      System.out.println("Response Time:" + respTime);
   	    try {
   	    	ThalesCommands thalesCommands= new ThalesCommands();
	        //Test NPCI ZPK 
	        String ezpk="A0C896C8E510FA5C2B809AB2EC6FD2BF";//2
	        String epvk="8E3985FD8754DECBBE9C62B518BC3114";
	        String  pan="456110000720";
	        String format="01";//Make it constant         
	        System.out.println("Before Diagrnostics ");
	         FSDMsg res=thalesCommands.diagnostics();
	         res.dump(System.out,"<--");
	         System.out.println("After Diagrnostics ");
	         System.out.println("Before Generate PVK");
	         res=thalesCommands.generatePVK();
	         res.dump(System.out,"<--");
	         System.out.println("After Generate PVK");
	         String defoffset="8258FFFFFFFF";
	         System.out.println("Before Generate IBMPIn");
	         res=thalesCommands.generateIBMPin(epvk,pan,defoffset);
	         res.dump(System.out,"<--");
	         System.out.println("After Generate IBMPIn");
	         
	         String epin=res.get("pin");
	         System.out.println("IBM PIN : " + epin);
	         System.out.println("Before Generate IBMPIn offset");
	         //03735  - encrypted pin
	         res=thalesCommands.generateIBMPinOffset(epvk,pan,epin);
	         res.dump(System.out,"<--");
	         System.out.println("After Generate IBMPIn offset");
	         String offset=res.get("offset");
	         System.out.println("Pin offset : " + offset);
	         //2834 is the PIN :2E08DEE23E1805C0
	         String pb="2E08DEE23E1805C0";// Set the new PIN from this pinblock
	         System.out.println("Before Translate Pin");
	         res=thalesCommands.tranlatePIN(ezpk, pan, pb, format);
	         res.dump(System.out,"Translate Pin <--");
	         System.out.println("After Translate Pin");
	         // JD 00 2881834389644
	         String npin=res.get("pin");
	         System.out.println("Before generate Pin Offset");
	         res=thalesCommands.generateIBMPinOffset(epvk,pan,npin);
	         res.dump(System.out,"<--");
	         System.out.println("After generate Pin Offset");
	         String newoffset=res.get("offset");//8258FFFFFFFF
	         
	         System.out.println("Before Verify Interchange Pin");
	         res=thalesCommands.verifyInterchangePin(ezpk, epvk, pan, pb, format, newoffset);
	         res.dump(System.out,"<--");
	         System.out.println("After Verify Interchange Pin");
	        
	     }
	     catch(Exception e)
	     {
	    	 System.out.println("Error in process: Class "+e.toString());
	     }
	      return true;
	}
   
}
