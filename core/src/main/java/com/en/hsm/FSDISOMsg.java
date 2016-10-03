package com.en.hsm;
import java.io.IOException;
 import java.io.PrintStream;
 import java.io.ObjectInput;
 import java.io.ObjectOutput;
 import java.util.Map;
 import java.util.Iterator;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
 
 public class FSDISOMsg extends ISOMsg {
     FSDMsg fsd;
     public  FSDISOMsg () {
         super();
     }
     public FSDISOMsg (FSDMsg fsd) {
         super();
         this.fsd = fsd;
     }
     public String getMTI() {
         return getString(0);
     }
     public byte[] pack() throws ISOException {
         try {
             return fsd.pack().getBytes();
         } catch (Exception e) {
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
     public FSDMsg getFSDMsg() {
         return fsd;
     }
     public String getString (int fldno) {
         String s = fsd.get (Integer.toString(fldno));
         return s;
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
         out.writeUTF (fsd.getBaseSchema());
         out.writeObject (fsd.getMap());
     }
     public void readExternal  (ObjectInput in)
         throws IOException, ClassNotFoundException
     {
         in.readByte();  // ignore version for now
         String basePath = in.readUTF();
         String baseSchema = in.readUTF();
         fsd = new FSDMsg (basePath, baseSchema);
         Map map = (Map) in.readObject();
         Iterator iter = map.entrySet().iterator();
         while (iter.hasNext()) {
             Map.Entry entry = (Map.Entry) iter.next();
             fsd.set ((String) entry.getKey(), (String) entry.getValue());
         }
     }
     private static final long serialVersionUID = 1L;
 } 