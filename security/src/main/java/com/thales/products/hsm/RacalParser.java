package com.thales.products.hsm;


import com.dpi.financial.ftcom.core.codec.Parser;

/*
 * @TODO
 * - Define ErrorField
 * - Support Optional Field.
 */
public class RacalParser extends AbstractParser {

    public Parser racal(){
        Parser nc=getParser(
                dummy()
        );


        Parser a0=getParser(
                UBString("mode", 1),
                UBString("key-type", 3),
                UBString("key-scheme-lmk", 1)
        );

        Parser a1=getParser(UBString("error", 2),
                TString("key-lmk", 16),
                UBString("check-value", 6)
        );

        Parser ci=getParser(UBString("bdk", 32),
                UBString("zpk-type",1),
                UBString("zpk",32),
                UBString("ksn-descriptor",3),
                UBString("ksn",16),
                UBString("pinblk",16),
                UBString("destination-pinblk-format",2),
                UBString("account-number",12)
        );



        Parser nd=getParser(UBString("error", 2),
                UBString("lmk-check-value", 16),
                UBString("firmware-number", 9)
        );
        Parser ee=getParser(
                TString("pvk",16),//again T-Type, can be variable
                UBString("offset",12),
                UBString("checklength",2),
                UBString("account-number",12),
                UBString("dectable", 16),
                UBString("pinvaldationdata", 12)
        );

        //Commented by chetan wrt Issue #5066 as on 19-Apr-2014
    	/* Parser ef=getParser(UBString("error", 2),
	              UBString("pin",5),// 
	              TString("pin2",1)  //added by chetan, handle extra bit in case of pin2 generation
	              );*/
        //Commented by chetan wrt Issue #5066 as on 19-Apr-2014
        //since ver. 2.0.22-P1 modified by Chetan w.r.t Issue #8514 as on 29-Nov-2014
        //Changed pin length as per HSM encrypted pin length
        Parser ef=getParser(UBString("error", 2),
                UBString("pin",13));


        //since ver. 2.0.22-P1 modified by Chetan w.r.t Issue #8514 as on 29-Nov-2014
        //Changed pin length as per HSM encrypted pin length
        Parser de=getParser(
                TString("pvk",16),//again T-Type, can be variable
                UBString("epin",13),
                UBString("checklength",2),
                UBString("account-number",12),
                UBString("dectable", 16),
                UBString("pinvaldationdata", 12)
        );
        Parser df=getParser(UBString("error", 2),
                UBString("offset",12)//
        );
   
    	  /*
    	   *  Translate Pin:   JE
    	   */
        Parser je=getParser(
                TString("zpk",16),//again T-Type, can be variable
                UBString("pinblk",8),//or 16
                UBString("pinblk-format",2),
                UBString("account-number",12)
        );
        //since ver. 2.0.22-P1 modified by Chetan w.r.t Issue #8514 as on 29-Nov-2014
        //Changed pin length as per HSM encrypted pin length
        Parser jf=getParser(
                UBString("error", 2),
                UBString("pin",13)//
        );
   
    	  /*
    	   *  translatePinTPKtoLMK Pin:   JC 
    	   */
        Parser jc=getParser(
                UBString("tpk",32),
                UBString("pinblk",16), //16 or 8
                UBString("pinblk-format",2),
                UBString("account-number",12)
        );
        //Modified by chetan wrt Issue #5066 as on 21 April 2014
        //since ver. 2.0.22-P1 modified by Chetan w.r.t Issue #8514 as on 29-Nov-2014
        //Changed pin length as per HSM encrypted pin length
        Parser jd=getParser(
                UBString("error", 2),
                UBString("pin",13)

        );
    	  
    	  /*
    	   * VerifyInterchangePIN : EA
           */
        Parser ea=getParser(
                UBString("zpk",16),
                UBString("pvk",16),
                UBString("maxpinlength",2),
                UBString("pinblk",8), //16 or 8
                UBString("pinblk-format",2),
                UBString("checklength",2),
                UBString("account-number",12),
                UBString("dectable",16),
                UBString("pinvaldationdata",12),
                UBString("offset",12)
        );

        Parser eb=getParser(
                UBString("error", 2)
        );
    	  
    	  /*
    	   * VerifyTerminalPINwithIBMAlgorithm : DA
           */
        Parser da=getParser(
                UBString("zpk",32),
                UBString("pvk",16),
                UBString("maxpinlength",2),
                UBString("pinblk",8), //16 or 8
                UBString("pinblk-format",2),
                UBString("checklength",2),
                UBString("account-number",12),
                UBString("dectable",16),
                UBString("pinvaldationdata",12),
                UBString("offset",12)
        );

        Parser db=getParser(
                UBString("error", 2)
        );
    	  
    	  /*
    	   * Generate CVK KeyPair
    	   */
/*    	  Parser as=getParser(
    			  UBString("delim",1),
	  			  UBString("reserved",1),
	  			  UBString("scheme",1),
	  			  UBString("kcv-type",1) 
	  			  );
    	  */
        Parser as=getParser(
                dummy()
        );
        Parser at=getParser(
                UBString("error", 2),
                //UBString("scheme",1),
                UBString("cvk-a",16),
                UBString("cvk-b",16),
                UBString("kcv-a",6),
                UBString("kcv-b",6)
        );
    	   /*
    	    * Generate VISACVV  : CW
    	  	*/
        Parser cw=getParser(
                UBString("keya",16),
                UBString("keyb",16),
                UBString("pan",12),
                UBString("delim",1),
                UBString("expdate",4),
                UBString("servicecode",1)

        );
        Parser cx=getParser(
                UBString("error", 2),
                UBString("cvv",3)//
        );
    	  /*
    	   * Verify VISA CVV : CY
    	   */
        Parser cy=getParser(
                UBString("keya",16),
                UBString("keyb",16),
                UBString("cvv",3),
                UBString("pan",12),
                UBString("delim",1),
                UBString("expdate",4),
                UBString("servicecode",3)
        );
        Parser cz=getParser(
                UBString("error", 2)
        );
    	 
    	 /*
    	  * Import ZPK  : A6
    	  */
        Parser a6=getParser(
                UBString("key-type",3),
                TString("zmk",16),
                TString("key-under-zmk",16),
                UBString("key-scheme",1)
        );
        Parser a7=getParser(
                UBString("error", 2),
                TString("key-under-lmk",16),
                UBString("check-value",6)
        );
    	   
    	   /*
    	   Parser kq=getParser(
    			   UBString("mode",1),
    			   UBString("scheme-id",1),
    			   TString("MK-AC",16),
    			   UBString("pan",12),
    			   UBString("atc",2),
    			   UBString("un",4),
    			   UBString("datalength",1),
    		       UBString("txndata",50),
    		       UBString("delim",1),
    		       UBString("cryptogram",8),
    		       UBString("arc",2)
    			   );
    	   */
    	   
     	  /*
     	   * Verify ARQC and Generate ARPC
     	   */
        Parser kq=getParser(
                UBString("mode",1),//<!-- 0,1,2 -->
                UBString("scheme-id",1),// <!-- 0,1 -->
                TString("MK-AC",16),
                //Changed fields to Binary
                UBStringBinary("pan",8),
                UBStringBinary("atc",2),
                UBStringBinary("un",4),
                UBString("datalength",1),
                UBStringBinary("txndata",48),
                UBString("delim",1),
                UBStringBinary("cryptogram",8),
                UBStringBinary("arc",2)
        );

        Parser kr=getParser(
                UBString("error",2),//<!-- 0,1,2 -->
                UBStringBinary("arpc",8)// <!-- 0,1 -->
        );
    	  
    	  /*
     	   * Decrypt encrypted pin
     	   * since ver. 2.0.22-P1 modified by Chetan w.r.t Issue #8514 as on 29-Nov-2014
	       * Changed pin length as per HSM encrypted pin length
		   * Modify by chetan wrt Issue #5066 as on 19-Apr-2014
     	   */
        Parser ng=getParser(
                UBString("account-number",12),
                UBString("epin",13)  //changed size 6 to send 13 digit encrypted pin
        );
        Parser nh=getParser(
                UBString("error",2),
                UBString("pin",13) //changed size 6 to get 13 digit clear pin
                //  UBString("refnumber",12)
        );
    	   
      	  /*
      	   * Gernerate Cust Selectable Pin : bk
           */
        Parser bk=getParser(
                UBString("key-type",3),
                UBString("zpk",16),
                UBString("pvk",16),
                UBString("pinblk",8), //16 or 8
                UBString("pinblk-format",2),
                UBString("checklength",2),
                UBString("account-number",12),
                UBString("dectable",16),
                UBString("pinvaldationdata",12)
        );
        Parser bl=getParser(
                UBString("error", 2),
                UBString("offset",12)

        );

     	  /*
     	   * Gernerate ZPK : ia
           */
        Parser ia=getParser(
                UBString("ezmk",16),
                //UBString("attala",1),
                UBString("delim",1),
                UBString("key-scheme-zmk",1),
                UBString("key-scheme-lmk",1),
                UBString("check-type",1)
        );

        Parser ib=getParser(
                UBString("error", 2),
                TString("key-under-zmk",16),
                TString("key-under-lmk",16),
                UBString("check-value",6)
        );


        //Translate PIN From one ZPK to other ZPK
        Parser cc=getParser(
                UBString("ezpk1",16),
                UBString("ezpk2",16),
                UBString("max-pin-len",2),
                UBString("pinblk1",16),
                UBString("pinblk-format1",2),
                UBString("pinblk-format2",2),
                UBString("account-number",12)
        );

        Parser cd=getParser(
                UBString("error",2),
                UBString("pin-len",2),
                UBString("pinblk2",16),
                UBString("pinblk-format2",2)
        );

        // translatePinLMKtoZPK
        Parser jg=getParser(
                UBString("zpk", 16),
                UBString("pinblk-format",2),
                UBString("account-number",12),
                UBString("pinblk",8)
        );
        Parser jh=getParser(
                UBString("error", 2),
                UBString("pinblk",16)

        );
        	 
		/*
		 * Generates a random TPK : HC
		 */
        Parser hc=getParser(
                UBString("ezmk",32),
                //UBString("attala",1),
                UBString("delim",1),
                UBString("key-scheme-zmk",1),
                UBString("key-scheme-lmk",1),
                UBString("check-type",1)
        );

        Parser hd=getParser(
                UBString("error", 2),
                TString("key-under-zmk",16),
                TString("key-under-lmk",16),
                UBString("check-value",6)
        );
        	 
			/*
	     	 * Encrypt clear pin under lmk
	     	 * since ver. 2.0.22-P1 modified by Chetan w.r.t Issue #8514 as on 29-Nov-2014
	     	 * Changed pin length as per HSM encrypted pin length
			 * Added by chetan wrt Issue #5066 as on 19-Apr-2014
	     	 */
        Parser ba=getParser(
                UBString("pin",13),
                UBString("account-number",12)
        );
        Parser bb=getParser(
                UBString("error",2),
                UBString("epin",13)
        );

        Parser parser=getParser(
                UBString("command",2),
                Switch("command",
                        "NC",nc,
                        "ND",nd,
                        "A0",a0,
                        "A1",a1,
                        "CI",ci,
                        "EE",ee,
                        "EF",ef,
                        "DE",de,
                        "DF",df,
                        "JE",je,
                        "JF",jf,
                        "EA",ea,
                        "EB",eb,
                        "AS",as,
                        "AT",at,
                        "CW",cw,
                        "CX",cx,
                        "CY",cy,
                        "CZ",cz,
                        "A6",a6,
                        "A7",a7,
                        "KQ",kq,
                        "KR",kr,
                        "NG",ng,
                        "NH",nh,
                        "IA",ia,
                        "IB",ib,
                        "BK",bk,
                        "BL",bl,
                        "CC",cc,
                        "CD",cd,
                        "JG",jg,
                        "JH",jh,
                        "JC",jc,
                        "JD",jd,
                        "DA",da,
                        "DB",db,
                        "HC",hc,
                        "HD",hd,
                        "BA",ba,
                        "BB",bb
                )
        );

        return parser;
    }
}