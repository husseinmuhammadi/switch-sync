package com.en.listener;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jdom.JDOMException;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

public class AtmNdc extends ISOMsg implements Cloneable  {
    AtmPackager fsd;
    public  AtmNdc () {
        super();
    }
    public AtmNdc (AtmPackager fsd) {
        super();
        this.fsd = fsd;
    }
    public String getMTI() {
        return getString(0);
    }
    public byte[] pack() throws ISOException {
        try {
            String packedValue= fsd.pack();
           /* byte[] b= packedValue.getBytes("cp1256");
            for(int i=0;i<b.length;i++){
            	if(b[i]<0){
            		b[i]=(byte)(b[i]-45);
            	System.out.print(b[i]);
            	System.out.print(",");
            	}
            }
            System.out.println();
            return b;*/
            System.out.println(packedValue);
            UTF2IranSystem uTF2IranSystem=new UTF2IranSystem();
            byte[] b=uTF2IranSystem.convert(packedValue);
            /*String r=ISOUtil.hexString(b);
            System.out.println(r);*/
            return b;
        }catch (Exception e) {
            throw new ISOException (e);
        }
    }
    public int unpack(byte[] b) throws ISOException {
        try {
            fsd.unpack (b);
            return b.length;
        } catch (Exception e) {
            throw new ISOException (e);
        }
    }
    public void unpack (InputStream in) throws IOException, ISOException {
        synchronized (this) {
            try {
                fsd.unpack(in);
            } catch (JDOMException e) {
                throw new ISOException (e);
            }
        }
    }

    public AtmPackager getFSDMsg() {
        return fsd;
    }
    public String getString (int fldno) {
        return fsd.get (Integer.toString(fldno));
    }
    
    public String getString (String fldKey) {
    	System.out.println("FLD : " + fldKey); 
        return fsd.get(fldKey);
    }
    
    public Object getValue (String fpath){
    	System.out.println("VAL : " + fpath); 
        return fsd.get(fpath);
    }
    
    public boolean hasField (int fldno) {
        return getString(fldno) != null;
    }
    
    public void dump (PrintStream p, String indent) {            
    	if (fsd != null) 
    		fsd.dump (p, indent);    		
    }
    
    public void writeExternal (ObjectOutput out) throws IOException {
        out.writeByte (0);  // reserved for future expansion (version id)
        out.writeUTF (fsd.getBasePath());
       // out.writeUTF (fsd.getBaseSchema());
        out.writeObject (fsd.getMap());
    }
    public void readExternal  (ObjectInput in) 
        throws IOException, ClassNotFoundException
    {
        in.readByte();  // ignore version for now
        String basePath = in.readUTF();
        String baseSchema = in.readUTF();
        fsd = new AtmPackager (basePath, baseSchema);
        Map map = (Map) in.readObject();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            fsd.set ((String) entry.getKey(), (String) entry.getValue());
        }
    }
    public Object clone() {
    	AtmNdc m = (AtmNdc) super.clone();
        m.fsd = (AtmPackager) fsd.clone();
        return m;
    }
/*    public Object clone(int[] fields) {
    	AtmNdc m = (AtmNdc) super.clone();
        m.fsd = new AtmPackager(fsd.getBasePath(), fsd.getBaseSchema());
        for (int i=0; i<fields.length; i++) {
            String f = Integer.toString(fields[i]);
            m.fsd.set (f, fsd.get (f));
        }
        return m;
    }
    public void merge (ISOMsg m) {
        if (m instanceof AtmNdc) {
            fsd.merge (((AtmNdc)m).getFSDMsg());
        } else {
            for (int i=0; i<=m.getMaxField(); i++) {
                if (m.hasField(i))
                    fsd.set (Integer.toString(i), m.getString(i));
            }
        }
    }*/
    public void setResponseMTI() {
        try {
            super.setResponseMTI();
        } catch (ISOException ignored) { }               
    }
    public void set (String name, String value) {
        if (value != null)
            this.fsd.set (name, value);
    }   
    private static final long serialVersionUID = 1L;
}

