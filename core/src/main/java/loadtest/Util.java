package loadtest;

//import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.jpos.iso.ISODate;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;

public class Util {
	 Date d;
	   public Util(Date d){
		   this.d=d;
	   }
	   public  String get_date() {  
	         return ISODate.getDateTime(d);
	    }  
	    
	    public   String get_Stan(){
	   		 return String.valueOf(System.currentTimeMillis() % 1000000);
	    }
	    
	    public String get_Stan(long number) {
	      try {
	        return ISOUtil.padleft(Long.toString(number), 6, '0');
	      } catch (ISOException ie){
	        return "000000";
	      }
	    }
	    
	    public String get_LocalTime(){   
	        String hhmmss =ISODate.getTime( d ) ;
	        return hhmmss; 
	    }
	    
	   public   String get_LocalDate(){
	        String MMDD = ISODate.getDate( d ) ;
	        return MMDD;
	    }
	    public   String get_CaptureDate(){
	        String MMDD = ISODate.getDate( d ) ;
	        return MMDD;
	    }
	    
	 public   String getRRN(String stan) throws ISOException{
		 System.out.println(d);
	     String jd=ISODate.getJulianDate(d); 
	     System.out.println("JulianDate:"+jd);
	     String gmt = ISODate.getDateTime(d,TimeZone.getTimeZone("GMT") ) ;
	     System.out.println("gmt:"+gmt);
	     
	     String rrn= jd+gmt.substring(4,6)+ISOUtil.padleft(stan+"",6,'0');
	     System.out.println("rrn:"+rrn);
	     
	     return rrn; 
	    }       
	 
	 public static void main(String[] args) throws Exception {
			Date d=new Date();
		 System.out.println((new Util(d)).getRRN("00001"));
	}

}
