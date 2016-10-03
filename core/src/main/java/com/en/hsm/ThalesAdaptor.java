package com.en.hsm;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jpos.iso.ISOUtil;
import org.jpos.q2.iso.MUXPool;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.space.SpaceUtil;
import org.jpos.util.NameRegistrar;
/*
 * DA/EE  EA/EE   EC
 * Key Schemes
 * ============
 * Z - Single DES
 * U - Encryption of Double Length Keys, Using varient method
 * T - Encryption of Triple Length Keys,Using varient method
 * X - Encryption of Double Length Keys, ANSI
 * Y - Encryption of Triple Length Keys, ANSI
 * 
 * Key Type Table
 * ===============
 * 00  ZMK
 * 01  ZPK
 * 
 * 02  PVK
 *     TPK
 *     TMK
 * 03  TAK
 * 
 * 05  PVK (Not on us)
 * 
 * 
 * Process:
 * 1)Import ZMK (2 clear components from npci)
 * 2)Import Encrypted ZPK  -  use ( IK)
 * 
 * 
 * 5)Generate & Verify Pinoffset   (GU & GV)
 * 6)Generate PIN :  offset & encrypted pin    DE
 * 
 * 7)print Pinmailers   Pin  ( PE & PF,PZ)
 * 
 * 
 * 
 * CVK - Key Type: 402  (BK or FK)
 * 
 * 
Zone Master Key Management
 
Method 1: In the traditional scheme of things, Zone Master Keys are made of 2 or more components,
          with each component being known to a custodian. You can simply gather the custodians,
          go the 9000 console and follow the appropriate import procedure. IMHO this is the most 
          secure way to do it since your organization can be certain that no single person gains 
          knowledge of the clear ZMK.

Method 2: Create a new ZMK at the 7000, call it ZMK-2. Encrypt/Export your ZMK under ZMK-2. 
          Import ZMK-2 at the 9000. Import ZMK encrypted under ZMK-2 at the 9000. If this is done
          properly, it can be as secure as method 1 but in order to do that you would need to 
          generate 2 or more ZMK components in order to form ZMK-2.


 */
public class ThalesAdaptor {
     
      public ThalesAdaptor() {
            }
 
	      protected FSDMsg createRequest(String command) {
	          FSDMsg req = new FSDMsg("file:./cfg/hsm-");
	          if (command != null)
	              req.set("command", command);
	          return req;
	      }

	    
	      protected FSDMsg command(FSDMsg request) throws Exception {
	          StringBuffer sbuffer = new StringBuffer(request.get("command"));
	          sbuffer.setCharAt(1, (char) (sbuffer.charAt(1) + 1));
	          //FSDMsg resp = createResponse(sbuffer.toString());

	          FSDISOMsg msg = new FSDISOMsg(request);
	          Space sp=SpaceFactory.getSpace();
	          long stan=SpaceUtil.nextLong(sp,"hc");
	         // String dd=Long.toHexString(stan);
	         // msg.setHeader(ISOUtil.hex2byte(ISOUtil.zeropad(dd, 8)));
	          msg.setHeader(ISOUtil.zeropad(stan+"", 4).getBytes());
	          //channel.setBasePath(resp.getBasePath());
	          //channel.setSchema(resp.getBaseSchema());
	          //channel.send(msg);
	         // ThalesChannel channel=(ThalesChannel)NameRegistrar.get("hsm-channel-adaptor");
	         // FSDISOMsg response = (FSDISOMsg) channel.receive();
	          //Space sp=SpaceFactory.getSpace();
	          //sp.out("test-send", msg);
	         // FSDISOMsg  r=(FSDISOMsg)sp.in("test-receive");
	         //  TMUX mux=(TMUX)NameRegistrar.get("mux.thales");
	          
	          //TMUX mux=(TMUX)NameRegistrar.get("hsm-mux");
	          MUXPool mux=(MUXPool)NameRegistrar.get("mux.hsm-mux");
	           FSDISOMsg resp=(FSDISOMsg)mux.request(msg, 15000);
	        //  resp.merge(response.getFSDMsg());
	         // return resp;
	         //return response.getFSDMsg();
	           if(resp!=null)
	          return resp.getFSDMsg();
	           else 
	        	   return null;
	      }

	 }
 
