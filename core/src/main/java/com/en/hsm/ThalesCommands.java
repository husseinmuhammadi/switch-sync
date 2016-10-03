package com.en.hsm;
 

public class ThalesCommands extends ThalesAdaptor{
	String dec="0123456789012345";
    String vd="0011440000N4";
    
   public FSDMsg diagnostics() throws Exception {
 	   FSDMsg request=createRequest("NC");
       return command(request);
   }
   

   public FSDMsg generateDoubleLengthKey1() throws Exception {
       FSDMsg req = createRequest("A0");
       req.set("mode", "0");
       req.set("key-type", "001");
       req.set("key-scheme-lmk", "U");
       return command(req);
   }
   public FSDMsg generateTMK()throws Exception{
       /*
 	  //String genTMK="A00002Z"; // U- double length,
       String genTMK="A00002U"; // U- double length,
       //1234A1 00 20D647D3E51DA5DB AC7358 (TMK)
       command("Gen TMK", genTMK);
    
       //PVK      //0235 A1 00 U 4A120E99EED917B4 55C83746B05973AB F5B101
       */
 	  FSDMsg req = createRequest("A0");
       req.set("mode", "0");
       req.set("key-type", "002");
       req.set("key-scheme-lmk", "U");
       return command(req);

       
   }
   public FSDMsg generateMKAC()throws Exception{
       FSDMsg req = createRequest("A0");
       req.set("mode", "0");
       req.set("key-type", "109");
       req.set("key-scheme-lmk", "U");
       return command(req);

       
   }
   
   public FSDMsg generatePVK()throws Exception{
	   FSDMsg req = createRequest("A0");
       req.set("mode", "0");
       req.set("key-type", "002");
       req.set("key-scheme-lmk", "U");
       return command(req);

//    String genTMK="HC"+"U4A120E99EED917B455C83746B05973AB"; // U- double length,
   //    command("Gen pvk", genTMK);
       //0235 HD 00 C20EA62BCEE6FC831AA87F99F82BB9E6
       
       //4990 HC 3539A14FFE17772C
       //4990 HD 00 B84707A40AFFC5FC 0CEE91A5B83001B4
       
       //5188 HC FE4C09EC7FC9B1BA
       //5188 HD 00 D4AE87B3F4365FB7 E1A22EA5B6FFD943
       
       
       //5196 HC FE4C09EC7FC9B1BA
       //5196 HD 00 631C2DEB488BDEB3 683A0BF2A34B19E8
       
       //5201 HC FE4C09EC7FC9B1BA
       //5201 HD 00 85094157E816FF29 8C0AF96DB40CCE60
   }
   public FSDMsg generatePrintFormat()throws Exception{
       //Print Format
        String format=
                   ">L>013^0"+
                   ">L013^1"+ ">041^P"+
                   ">L013^2"+
                   ">L013^3"+
                   ">L013 THNAK YOU"+
                   ">L"+  //Line Feed
                   ">F"; //Form Feed
                   ;
    //   command("Print Format", cmd);
                   FSDMsg req = createRequest("PA");
                   req.set("format", format);
                   return command(req);

   }
   public void printPINMailer()throws Exception{
	   String pan="144000006432";
       String format=">L>L>L>L>L>L>L>L>L>025^P>050^0>L>050^1>L>050^2^3^4>L>050^5^6^7>L>L>050^8>064^9>L>050^A>057^B>067^C>077^D>017>L>L>L>L>L>L>L>L>L";
       //command("PrintFormat", cmd);
       FSDMsg req = createRequest("PA");
       req.set("format", format);
       FSDMsg res=command(req);
      
       //cmd="PE"+"C"+pan+"98779";
       String addr="Bharavi G.;Surya NAGAR  ;Old Alwal;; ;Secundrabad;;INDIA;Card Number:;xxxxx64324;Date:;01/07/2011;Pin Code:;560010";
      // command("PinMailer",cmd+addr);
        //0006 PF 00 3018185465422469654315694
       req = createRequest("PE");
       req.set("format","C"+pan+"98779"+addr);
        res=command(req);

      }
   public FSDMsg generateIBMPin(String epvk,String pan,String offset) throws Exception{
       
       //String key="U1BD6E6C5AB24D30C9BA150612C3871AB";

          
       FSDMsg req=createRequest("EE");
       req.set("pvk-type","U");//make this as Constanct Key Scheme
       req.set("pvk",epvk);
       //req.set("offset","1435FFFFFFFF");//make it as param
       req.set("offset",offset);//make it as param
       req.set("checklength","04");
       req.set("account-number",pan);
       req.set("dectable",dec);
       req.set("pinvaldationdata",vd);
       //String cmd="EE"+epvk+"1235FFFFFFFF"+"04"+pan+dec+vd;
       return command(req);
       
       //Generate IBM PIN
       // EE +PVK(16H)+ OFFSET(12H)+MIN PIN LEN(2N)+ PAN(12N)+dec(16N)+PIN VALIDATION DATA
       
   }
   /*
    * Required in Change PIN
    * Use this function to get the encrypted pin under LMK
    * then calculate offset for the encrypted pin for storing offset
    */
   public FSDMsg tranlatePIN(String ezpk,String pan,String pb,String format)throws Exception{
	   FSDMsg req=createRequest("JE");
	   req.set("zpk-type","U");//make this as Constanct Key Scheme
       req.set("zpk",ezpk);
       req.set("pinblk",pb);
       req.set("pinblk-format",format);
       req.set("account-number",pan);
       return command(req);
   }
   public FSDMsg generateIBMPinOffset(String epvk,String pan,String epin) throws Exception{
       //Ex
       //0302 DE 10BAF35E518E642D 2 881834389644 04 415958191322 0123456789012345 4034159581N1
       //0302 DF 00 9872 FFFFFFFF
        String key="U1BD6E6C5AB24D30C9BA150612C3871AB";
       String ep="78575";
       //Generate IBM PIN OFFSET
       //DE+PVK+PIN(LN or LH)+min PIN LENGTH + DEC TABLE + VALIDATION DATA
      // String cmd="DE"+key+ep+"04"+pan+dec+vd;
      // command("OFFSET",cmd);
       //0238 DF 02 1234FFFFFFFF
       FSDMsg req=createRequest("DE");
       req.set("pvk-type","U");//make this as Constanct Key Scheme
       req.set("pvk",epvk);
       req.set("epin",epin);//make it as param
       req.set("checklength","04");
       req.set("account-number",pan);
       req.set("dectable",dec);
       req.set("pinvaldationdata",vd);
       return command(req);
   }
 
   public FSDMsg verifyInterchangePin(String ezpk,String epvk,String pan,String pb,String format,String offset) throws Exception{
	   FSDMsg req=createRequest("EA");
	   req.set("zpk-type","U");//make this as Constanct Key Scheme
       req.set("zpk",ezpk);
       req.set("pvk-type","U");//make this as Constanct Key Scheme
       req.set("pvk",epvk);
       req.set("maxpinlength","12");
       req.set("pinblk",pb);
       req.set("pinblk-format",format);
       req.set("checklength","04");
       req.set("account-number",pan);
       req.set("dectable",dec);
       req.set("pinvaldationdata",vd);
       req.set("offset",offset);
       return command(req);
   }
   /*
    * CVV Key Pair Generation
    */
   public FSDMsg generateCVKPair() throws Exception{
       FSDMsg req=createRequest("AS");
       req.set("delim",";");
       req.set("reserved","0");
       req.set("scheme","0");
       req.set("kcv-type","0");
       
       return command(req);
       
   }
   public FSDMsg generateVISACVV(String keya,String keyb,String pan,String exp,String service)throws Exception{
      // String key="C62B3BE16F8E1F61B749C78E1EFB3FEB";
      // String expDate="0909";
      // String serviceCode="000";
      // String cmd="CW"+key+pan+";"+expDate+serviceCode;
      // command("cvv gen", cmd);
	   
	   FSDMsg req=createRequest("CW");
       req.set("keya",keya);
       req.set("keyb",keyb);
       req.set("pan",pan);// 12 is fixed length
       req.set("delim",";");
       req.set("expdate",exp);
       req.set("servicecode",service);
       return command(req);
       //0235 CX 00 754
     }
     public FSDMsg verifyVISACVV(String keya,String keyb,String pan,String cvv,String exp,String service)throws Exception{
          FSDMsg req=createRequest("CY");
         req.set("keya",keya);
         req.set("keyb",keyb);
         req.set("cvv",cvv);
         req.set("pan",pan);// 12 is fixed length
         req.set("delim",";");
         req.set("expdate",exp);
         req.set("servicecode",service);
         return command(req);
       }
     public FSDMsg generatePVKPair(String ezmk) throws Exception{
         FSDMsg req=createRequest("FG");
         //
         req.set("zmk-type","U");
         req.set("zmk",ezmk);
         
         req.set("delim",";");
         req.set("schemezmk","X");
         req.set("schemelmk","U");
         req.set("kcvtype","1");
         return command(req);
         
     }
     /*
      * Mode of operation:
    		 0 = Perform ARQC verification only
    		 1 = Perform ARQC Verification and ARPC generation
    		 2 = Perform ARPC Generation only
    	 Identifier of the EMV scheme;
				0 = Visa VSDC or UKIS
				1 = Europay or MasterCard M/Chip
		 IMK  32H or 1+32H
		 IV   for EMV 2000 Application Cryptogram session key derivation(16B)
		 PAN+SEQ  Pre-formatted PAN/PAN Sequence No.(8B)
		 ATC   2B   Application Transaction Counter. 
		          Present for all modes. Any two byte value must be supplied,though it is not used, for Scheme ID = 0.
		 UN    4B  Unpredictable Number.  Present for all modes.  Any four byte value must be supplied, though it is not used, for Scheme ID = 0
		 Transaction Data Length 
		 Transaction Data
		 Delimiter  ;      Only present for Modes 0 and 1.
		 ARQC/TC/AAC    8bytes
		 ARC            2bytes 		
		  test.verifyARQC(mode,SchemeID,imkAC, pan, atc, un, arqc, arc, TransactionData);
			       
      */
     public FSDMsg verifyARQC(String mode,String scheme,String imk,String pan,String atc,String un,String arqc,String arc,String data)throws Exception{
    
    	 FSDMsg req=createRequest("KQ");
         
    	 req.set("mode",mode);//<!-- 0,1,2 -->
    	 req.set("scheme-id",scheme);// <!-- 0,1 -->
    	 req.set("MK-AC",imk);
    	 req.set("pan",pan);
    	 req.set("atc",atc);
    	 req.set("un",un);
    	 req.set("datalength","1D");
    	 req.set("txndata",data);
    	 req.set("delim",";");
    	 req.set("cryptogram",arqc);
    	 req.set("arc",arc);
        return command(req);
    	 
     }
     public FSDMsg importZPK(String keyType,String ezmk,String zpk,String keyScheme) throws Exception{
    	//   <field id='key-type' type='A' length='3'/>
    	///   <field id='zmk' type='A' length='33'/>
    	//   <field id='key-under-zmk' type='A' length='33'/>
    	//   <field id='key-scheme' type='A' length='1'/>
    	   FSDMsg req=createRequest("A6");
           //
           req.set("key-type",keyType);
           req.set("zmk",ezmk);
           req.set("key-under-zmk",zpk);
           req.set("key-scheme",keyScheme);
           req.set("attala","0");
           return command(req);
     }
   
   /*
	  public void importZMK() throws Exception{
          //ZMK :    0102030405060708 0102030405060708
           //Encrypted ZMK component: 43E4 FCF5 046E 0ED3 43E4 FCF5 046E 0ED3
           //Key check value: 5E8E 6DBA 86F4 8E74
           String cmd="GG"+"43E4FCF5046E0ED3"+"43E4FCF5046E0ED3"
                           +"43E4FCF5046E0ED3"+"43E4FCF5046E0ED3"
                           +"43E4FCF5046E0ED3"+"43E4FCF5046E0ED3";
           command("import ZMK", cmd);
           //command Z
           //ZMK:0235 GH 00 9BFC28B1D4DDCE4A9BFC28B1D4DDCE4A 5E8E6DBA86F48E74 ("Response)
      }
      public void translateZPKFromtoLMK() throws Exception{
          ///////////////////////////////////////////////////////////
          ///  Translate ZPK from ZMK to LMK
          //   FA+ ZMK + ZPK
          /////////////////////////////////////////////////
          String cmd="FA"+"9BFC28B1D4DDCE4A9BFC28B1D4DDCE4A"+"5BD3C08FC5F8D226B0ED83B59295F033";
          command("Translate ZPK", cmd);
      } 
      public void generateZPK() throws Exception{
          ////////////////////////////////////////////////////////////////////////////////////
          ////  Generate ZPK /////////////////
          ////////////////////////////////////////////////////////// 
         String cmd="IA"+"9BFC28B1D4DDCE4A"+"9BFC28B1D4DDCE4A";
         command("Create ZPK", cmd);
          
          //
          //ZPK:0235 IB 00 5BD3C08FC5F8D226 B0ED83B59295F033 03DEBA99F610E65D (under ZMK)
          //                   8438FFBAD142283F74603E0D516AE286 F3E1AAF7714A8A19
          //ZPK:0235 FB 00 B0ED83B59295F03303DEBA99F610E65DB0ED83B59295F033
          
      }
   
         
      public void encryptClearPIN()throws Exception{
          String pan="144000006432";
          String cmd="BA"+"1234FFFFFFFFFFFF"+pan;
          command("EncryptPIN", cmd);
      }
     
      
      public void generateKey()throws Exception{
          String cmd="A0"+"0"+"002"+"U";
          command("keygen", cmd);
          
          //002 - PVK
          //0235 A1 00 U 1BD6E6C5AB24D30C9BA150612C3871AB 7F3E06
          
      }
      public void generareRandomPin()throws Exception{
          String cmd="JA"+pan;
          command("randompin", cmd);
          //0237 JB 00 98779
      }
    
      
      public FSDMsg importDoubleLengthKey(String zmk, String key) {
          FSDMsg req = createRequest("A6");
          req.set("key-type", "001");
          req.set("zmk", "X" + zmk);
          req.set("key-under-zmk", "X" + key);
          req.set("key-scheme", "X");

         return req;
      }
*/

}
