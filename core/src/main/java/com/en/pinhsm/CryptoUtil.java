
package com.en.pinhsm;
import java.security.Key;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

 

/**
 * @author bharavi
 */
public class CryptoUtil {
	private static Cipher cipher=null;
	private static CryptoUtil crypto=null;
	public static int ENCRYPT_MODE=Cipher.ENCRYPT_MODE;
	public static int DECRYPT_MODE=Cipher.DECRYPT_MODE;
	private CryptoUtil(){
	}
    public static byte[] desede(byte[] data,byte[] key,int mode) throws Exception
    {
    Cipher cipher=Cipher.getInstance("DESede/ECB/NoPadding");
   // RawKey rk=new RawKey("DESede",key);
     Key rk=getSecretKey(key);
    cipher.init(mode,rk);
    byte[] enc=cipher.doFinal(data);
    return enc;
   }
    public static byte[] desedeCBC(byte[] data,byte[] key,int mode) throws Exception
    {
    Cipher cipher=Cipher.getInstance("DESede/CBC/NoPadding");
   // RawKey rk=new RawKey("DESede",key);
    byte[] ivb=new byte[8];
    IvParameterSpec iv=new IvParameterSpec(ivb);
     Key rk=getSecretKey(key);
    cipher.init(mode,rk,iv);
    byte[] enc=cipher.doFinal(data);
    return enc;
   }
    public static byte[] desCBC(byte[] data,byte[] key,int mode) throws Exception
    {
    Cipher cipher=Cipher.getInstance("DES/CBC/NoPadding");
   // RawKey rk=new RawKey("DESede",key);
    byte[] ivb=new byte[8];
    IvParameterSpec iv=new IvParameterSpec(ivb);
     Key rk=getSecretKey(key);
    cipher.init(mode,rk,iv);
    byte[] enc=cipher.doFinal(data);
    return enc;
   }
    public static byte[] des(byte[] data,byte[] key,int mode) throws Exception
    {
	    Cipher cipher=Cipher.getInstance("DES/ECB/NoPadding");
	     Key rk=getSecretKey(key);
	     cipher.init(mode,rk);
	     byte[] enc=cipher.doFinal(data);
	     return enc;
   }
    
    public static byte[] getKCV(byte[] key) throws Exception
    {
	    String alg="DES/ECB/NoPadding";
	    if(key.length>8) alg="DESede/ECB/NoPadding"; 
	    Cipher cipher=Cipher.getInstance(alg);
	     Key rk=getSecretKey(key);
	     byte[] data={0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0};
	     cipher.init(Cipher.ENCRYPT_MODE,rk);
	     byte[] enc=cipher.doFinal(data);
	     return  ArrayUtil.subarray(enc,0,3);
	    // return enc;
   }
    

	private static CryptoUtil getInstance(){
		if(crypto==null) crypto=new CryptoUtil();
		return crypto;
	}
      public static SecretKey getSecretKey(byte[] keyData)throws Exception{
            KeySpec ks = null;
            SecretKey ky = null;
            SecretKeyFactory kf = null;
         
            
            String alg;//="DESede";
            if(keyData.length==8)
            {
                ks = new DESKeySpec(keyData);
                alg="DES";
            }else  if(keyData.length==16){
            	byte[] XL=new byte[8];
				byte[] XR=new byte[8];
				ArrayUtil.split(keyData,XL,XR);
		    	byte[] mer=ArrayUtil.concat(XL,XR,XL);
				ks = new DESedeKeySpec(mer);
	            alg="DESede";
	        
            } else{
            ks = new DESedeKeySpec(keyData);
            alg="DESede";
        
            }
            kf = SecretKeyFactory.getInstance(alg);
            
            ky = kf.generateSecret(ks);
            return ky;
         
    }
      //ISO9797 Padding Method 1
      public static byte[] padISO9797M1(byte[] data)
      {
          int j=data.length;
          int k=j%8;
          if(k!=0) k=8-k;
          byte[] abyte1=new byte[j+k];
          System.arraycopy(data,0,abyte1,0,j);
          return abyte1;
      }
      public static String padStr(String data)
      {
          int j=data.length();
          int k=j%8;
          if(k!=0) k=8-k;
          String sp="        ";
          System.out.println(data.length()+":"+k);
          System.out.println(sp.substring(0,k).length());
          return data+sp.substring(0,k);
      }    
      public static byte[] padISO9797M2(byte[] data)
      {
          int j=data.length;
          int k=j%8;
          if(k!=0) k=8-k;
          else
          	k=8;
          byte[] abyte1=new byte[j+k];
          System.arraycopy(data,0,abyte1,0,j);
          abyte1[j]=(byte)0x80;
          return abyte1;
          
      }
	public static byte[] udkEMV200(byte[] MK,byte[] IV,int ATC,int B,int H)throws CryptoException{
			byte[] GP=IV;
			byte[] P=MK;
			int a[]=ArrayUtil.bArray(ATC,B,H);
			for(int i=0;i<H-1;i++) {
				byte[] T=P;
				P=encryptEMV2000(P,GP,a[i]);
				GP=T;
			//System.out.println("GP["+i+"]:"+Conversion.byteArray2String(GP));
			//System.out.println(" P["+i+"]:"+Conversion.byteArray2String(P));
			}
			byte[] SK=encryptEMV2000(P,GP,a[H-1]);
			SK=ArrayUtil.xor(SK,GP);
			ArrayUtil.setOddParity(SK);
			return SK;
		}
		private static byte[] encryptEMV2000(byte[] X,byte[] Y,int j)throws CryptoException{
					//int h=8; //tree height
					int b=4; //no of children
					
					byte[] XL=new byte[8];
					byte[] XR=new byte[8];
					ArrayUtil.split(X,XL,XR);
					byte[] YL=new byte[8];
					byte[] YR=new byte[8];
					ArrayUtil.split(Y,YL,YR);
			
					String mod=Integer.toHexString(j%b);	
					byte[] bMod=Conversion.hex2byte(mod);
					byte[] result=ArrayUtil.xor(bMod,YL);
					byte[] ZL=CryptoUtil.des3ECB(ArrayUtil.concat(XL,XR,XL),result);		
					byte[] re1=ArrayUtil.xor(bMod,YR);
					byte[] p={(byte)0xF0};
					byte[] re2=ArrayUtil.xor(re1,p);
					byte[] ZR=CryptoUtil.des3ECB(ArrayUtil.concat(XL,XR,XL),re2);
					return ArrayUtil.concat(ZL,ZR);

		}
		/*
		public static byte[] decrypt(byte[] X,byte[] Z,int j)throws CryptoException{
			              //  int h=8; //tree height
							int b=4; //no of children
					
							byte[] XL=new byte[8];
							byte[] XR=new byte[8];
							ArrayUtil.split(X,XL,XR);
							byte[] ZL=new byte[8];
							byte[] ZR=new byte[8];
							ArrayUtil.split(Z,ZL,ZR);
			
							String mod=Integer.toHexString(j%b);	
							byte[] bMod=Conversion.hex2byte(mod);
							byte[] result=CryptoUtil.des3ECBINV(ArrayUtil.concat(XL,XR,XL),ZL);
						
							byte[] YL=ArrayUtil.xor(result,bMod);	
							byte[] re1=CryptoUtil.des3ECBINV(ArrayUtil.concat(XL,XR,XL),ZR);
							byte[] re2=ArrayUtil.xor(re1,bMod);
							byte[] p={(byte)0xF0};
							byte[] YR=ArrayUtil.xor(re2,p);
							return ArrayUtil.concat(YL,YR);
		}
		*/
	private  byte[] des3ECBimpl(byte[] key,byte[] data,int mode) throws CryptoException{
		try{
		if(cipher==null)
		cipher = Cipher.getInstance("DESede/ECB/NoPadding");
         Key rk=getSecretKey(key);
		cipher.init(mode,rk);
		byte[] result=cipher.doFinal(data);
		return result;
		}catch(Exception e){
			e.printStackTrace();
			throw new CryptoException(e.getMessage());
		}
		
	}
	public static byte[] des3ECB(byte[] key,byte[] data) throws CryptoException{
		return CryptoUtil.getInstance().des3ECBimpl(key,data,Cipher.ENCRYPT_MODE);
	}
	public static byte[] des3ECBINV(byte[] key,byte[] data) throws CryptoException{
			return CryptoUtil.getInstance().des3ECBimpl(key,data,Cipher.DECRYPT_MODE);
		}
}

