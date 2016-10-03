package loadtest;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jpos.iso.ISODate;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;

import com.en.loaddata.CardDetails;
import com.en.std.utils.IranSystem;

public class TestCards {
	
	static long stan = 200007;
	static String rrn = "531111";
	
	public TestCards(){
	}
	
	//card number 30 is card[29]
	public int getCard(int number){
		return number%50; 	
	}
	
	public ISOMsg getTestMsg(long number, CardDetails cardDetails) throws Exception{
		Date d=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
		String dat = sdf.format(d);
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("mmssSSS");
		String dat1 = sdf1.format(d);
		
		if(!cardDetails.getMti().equalsIgnoreCase("0420") && !cardDetails.getMti().equalsIgnoreCase("0400")
				&& !cardDetails.getMti().equalsIgnoreCase("0220")) {
			stan = Integer.parseInt(dat1.substring(0, 6));
			System.out.println("stan : " + stan);
			rrn = 	"53" + dat.substring(0, 4) + dat1.substring(0, 6);
			stan = stan + 1000;
		}
		
		//Updated by bhalchandra wrt Issue#5156 on 24-Apr-2014
		String cvv2 = cardDetails.getCvv2();
		String lang = "00";// 01:english 00:farsi
		String processCode = "";
		String field48 = "  " + cvv2 + lang;
		if(cardDetails.getFld3() != null) {
			processCode = cardDetails.getFld3().substring(0, 2);
		}
		
		if(processCode.equalsIgnoreCase("17")){
			String reserve = "        ";
	        String billType = cardDetails.getBillType();
	        String billCode = cardDetails.getBillCode();
	        String payCode = cardDetails.getPayCode();
	        cvv2 = cardDetails.getCvv2();
		    lang = "01";
	        field48 = "";
	        field48 = "  " + cvv2 + lang + reserve + billType + billCode + payCode + " 000000000000000000";
		}
		    
		//String pinkey = "C248B87D6AE33F9A";
		//String pin = "0605";
	    //byte[] pinBlock = Mac.instance.pinEnc(pin, "5859476520106967",ISOUtil.hex2byte(pinkey));
	    
        Util util=new Util(d);
        //String stan=util.get_Stan(number+1);
     	//String rrn=util.getRRN(stan);
        
     	ISOMsg m=new ISOMsg();

     	m=new ISOMsg(cardDetails.getMti());
     	
     	if(processCode.equalsIgnoreCase("33")){
     		
     		field48 = "  " + cvv2 + lang + "16" + cardDetails.getPan2();
     	} else if(processCode.equalsIgnoreCase("40") || processCode.equalsIgnoreCase("46") || processCode.equalsIgnoreCase("47"))
     	{
     		//m=new ISOMsg("0200");
     		field48 = "  " + cvv2 + lang + "16" + cardDetails.getPan2();
     	}
     	//stan = stan + 1;
     	String rrn1 = rrn ;
     	System.out.println("RRN = " + rrn1);
     	
 		//m.setHeader(ISOUtil.hex2byte("16010200CF0000000000000000000000000000000000"));
     	if(cardDetails.getPan() != null) {
     		m.set(2,cardDetails.getPan());
     	}
     	
		if(cardDetails.getMti().equalsIgnoreCase("0800") || cardDetails.getMti().equalsIgnoreCase("0820")) {
			m.set(15,"1114");
			field48 = "000000";
			m.set(53,"0000000000000000");
			m.set(70,"201");
			m.set(96,ISOUtil.hex2byte("0000000000000000"));
		}
     	

      if(cardDetails.getMti().equalsIgnoreCase("0500") || cardDetails.getMti().equalsIgnoreCase("0502")) {
    	  m.set(15,"0329");
    	  m.set(50, "364");
    	  m.set(74,"0000002517");
    	  m.set(75,"0000000001");
    	  m.set(76,"0000001045");
    	  m.set(77,"0000000055");
    	  m.set(78,"0000000918");
    	  m.set(79,"0000000000");
    	  m.set(80,"0000000000");
    	  m.set(81,"0000001812");
    	  m.set(82,"000000001000");
    	  m.set(83,"000017683025");
    	  m.set(84,"000000250000");
    	  m.set(85,"000000881025");
    	  m.set(86,"0000007645327733");
    	  m.set(87,"0000000015200000");
    	  m.set(88,"0000007853053349");
    	  m.set(89,"0000000082850000");
    	  m.set(97,"D0000000258822616");
    	  m.set(99,"9000");
      }
	      
		if(cardDetails.getFld3() != null) {
			m.set(3,cardDetails.getFld3());
		}
	 	if(cardDetails.getAmount() != null) {
	 		m.set(4,cardDetails.getAmount()); //000000000500
	 		//m.set(6,cardDetails.getAmount());
	 	}
	 	if (cardDetails.getBillingAmount() != null) {
	 		m.set(6,cardDetails.getBillingAmount());
	 	}
	 	if (cardDetails.getBillingCcy() != null) {
	 		m.set(51,cardDetails.getBillingCcy());
	 	}
		m.set(7,dat);
		if(!cardDetails.getMti().equalsIgnoreCase("0800") && !cardDetails.getMti().equalsIgnoreCase("0820")) {
			m.set(10, "00000001");
		}
		m.set(11,(new Long(stan)).toString()); // increment stan
		if (cardDetails.getMti().equalsIgnoreCase("0800") || cardDetails.getMti().equalsIgnoreCase("0820")) {
			m.set(11,"143958");
			m.set(7,"1113145920");
		}
		if(!cardDetails.getMti().equalsIgnoreCase("0800") && !cardDetails.getMti().equalsIgnoreCase("0820")) {
			m.set(12,ISODate.getTime(d));
			m.set(13,ISODate.getDate(d));
		}
		if (cardDetails.getExpDate() != null) {
			m.set(14,cardDetails.getExpDate());
		}
		//m.set(15,ISODate.getDate(d));
		//m.set(18,cardDetails.getMcc());
		if(cardDetails.getMcc() != null) {
			m.set(18,cardDetails.getMcc());
		}
		
		m.set(19,cardDetails.getCcy());
		m.set(22,"901");
		m.set(17, "1214");
		//m.set(17, ISODate.getDate(d));
		if (cardDetails.getMti().equalsIgnoreCase("0800") || cardDetails.getMti().equalsIgnoreCase("0820")) {
			m.set(17, "1112");
		}
		m.set(25, cardDetails.getPosConditionCode());
		m.set(26, "04");
		m.set(32, "627488");
		//m.set(32, "585947");
		if (cardDetails.getMti().equalsIgnoreCase("0800") || cardDetails.getMti().equalsIgnoreCase("0820")) {
			m.set(32, "585947");
		}
		//m.set(32, "585947");
		//m.set(32, "581672142");
		m.set(33, "585947");
		if (cardDetails.getTrack2() != null) {
			m.set(35,cardDetails.getTrack2());
		}
		if(!cardDetails.getMti().equalsIgnoreCase("0800") && !cardDetails.getMti().equalsIgnoreCase("0820")) {
			m.set(37,rrn1);
	
			m.set(41,"12312522");
			//m.set(41,"01222");
			m.set(42,"000000000582431");
			
			/*
			byte[] b = string.getBytes();
			byte[] b = string.getBytes(Charset.forName("UTF-8"));
			byte[] b = string.getBytes(StandardCharsets.UTF_8); // Java 7+ only
			*/
			m.set(43,new IranSystem("SHEPAH BANK           TEHRAN       THRIR").getBytes());
		}
		m.set(48, field48);
		if(cardDetails.getPin() != null) {
			m.set(52,ISOUtil.hex2byte(cardDetails.getPin()));
		}
		if(cardDetails.getCcy() != null) {
			m.set(49,cardDetails.getCcy());
		}
		if(cardDetails.getCcy() != null) {
			m.set(51,cardDetails.getCcy());
		}
		//m.set(49, "364");
		//m.set(51, "364");
		if(cardDetails.getMti().equalsIgnoreCase("0420") || cardDetails.getMti().equalsIgnoreCase("0400")) {
			m.set(95,"000000000000000000000000C00000000C00000000");
		}
		if(cardDetails.getPinBlock() != null) {
			m.set(52, new Mac().pinEnc(cardDetails.getPin(), cardDetails.getPan()));
			//m.set(52, ISOUtil.hex2byte("921B37CF8E83A6F3"));  //5859476520000400
			//m.set(52, ISOUtil.hex2byte("B970CBCD16C461DB"));  //5859476520108575
					//ISOUtil.hex2byte("A37998533D315B16"));
		}
		if(!cardDetails.getMti().equalsIgnoreCase("0800") && !cardDetails.getMti().equalsIgnoreCase("0820")) {
			m.set(100, "585947");
		}
		
		if(cardDetails.getAdditionalData() != null) {
			m.set(62, cardDetails.getAdditionalData());
		}
		
		//m.set(128, ISOUtil.hex2byte("2750243D43A12A00"));
		m.set(128, ISOUtil.hex2byte("2750243D43A12A00"));
	    return m;
	}
	

}
