package com.dpi.financial.ftcom.core.atm.iso.ndc.header;

import com.dpi.financial.ftcom.model.type.ascii.ControlCharacter;
import com.dpi.financial.ftcom.model.type.atm.ndc.MessageIdentifier;
import org.jpos.iso.ISOHeader;
import org.jpos.iso.ISOUtil;
import org.jpos.util.Loggeable;
import sun.nio.cs.StandardCharsets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by h.mohammadi on 1/9/2017.
 */
public class NdcHeader implements ISOHeader, Loggeable {
    private static final long serialVersionUID = -4728476002790138899L;

    private ByteArrayOutputStream out;
    private PrintStream p;

    private MessageIdentifier identifier;

    public NdcHeader() {
        try {
            out = new ByteArrayOutputStream();
            p = new PrintStream(out, false, "US-ASCII");
        } catch (UnsupportedEncodingException ignored) {
            // US-ASCII is a supported encoding
        }
    }

    @Override
    public Object clone() {
        try {
            NdcHeader h = (NdcHeader) super.clone();
            // if (this.header != null) h.header = this.header.clone();
            return h;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override
    public byte[] pack() {
        out.reset();
        p.print(ControlCharacter.STX.getCharacter());
        p.print(identifier.getValue());
        p.print(ControlCharacter.FS.getCharacter());
        p.flush();
        return out.toByteArray();
    }

    @Override
    public int unpack(byte[] b) {
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        int stx = in.read();

        StringBuilder builder = new StringBuilder();
        int i;
        while ((i = in.read()) != ControlCharacter.FS.getValue()) {
            builder.append((char)i);
        }
        setIdentifier(MessageIdentifier.getInstance(builder.toString()));
        return 0;
    }

    @Override
    public int getLength() {
        return 4;
    }

    @Override
    public void dump(PrintStream p, String indent) {
        p.println(indent + "<header>" + ISOUtil.hexString(pack()) + "</header>");
    }

    @Override
    public void setDestination(String dst) {

    }

    @Override
    public String getDestination() {
        return null;
    }

    @Override
    public void setSource(String src) {

    }

    @Override
    public String getSource() {
        return null;
    }

    @Override
    public void swapDirection() {

    }

    public MessageIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(MessageIdentifier identifier) {
        this.identifier = identifier;
    }
}
