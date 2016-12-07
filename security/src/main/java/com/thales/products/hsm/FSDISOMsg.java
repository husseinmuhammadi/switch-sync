package com.thales.products.hsm;

import java.io.PrintStream;

import com.dpi.financial.ftcom.core.codec.Parser;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;


public class FSDISOMsg extends ISOMsg {
    Parser fsd;

    public FSDISOMsg() {
        super();
    }

    public FSDISOMsg(Parser fsd) {
        super();
        this.fsd = fsd;
    }

    public String getMTI() {
        return getString(0);
    }

    public byte[] pack() throws ISOException {
        try {
            return fsd.pack();//.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ISOException(e);
        }
    }

    public int unpack(byte[] b) throws ISOException {
        try {
            //System.out.println("<=="+ISOUtil.hexString(b));
            fsd.unpack(b);
            return b.length;
        } catch (Exception e) {
            throw new ISOException(e);
        }
    }

    public Parser getFSDMsg() {
        return fsd;
    }

    // public String getString (int fldno) {
    //    String s = fsd.get (Integer.toString(fldno));
    //   return s;
    //}
    public boolean hasField(int fldno) {
        return getString(fldno) != null;
    }

    public Object getField(String name) {
        return fsd.getValue(name);
    }

    public void dump(PrintStream p, String indent) {
        if (fsd != null)
            fsd.dump(p, indent);
    }

    private static final long serialVersionUID = 1L;
}