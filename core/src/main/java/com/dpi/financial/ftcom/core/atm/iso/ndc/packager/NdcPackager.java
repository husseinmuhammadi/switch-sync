package com.dpi.financial.ftcom.core.atm.iso.ndc.packager;

import com.dpi.financial.ftcom.core.ndc.exception.NdcException;
import com.dpi.financial.ftcom.core.atm.iso.ndc.msg.NdcMsg;
import com.dpi.financial.ftcom.model.type.ascii.ControlCharacter;
import org.jpos.iso.ISOComponent;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.util.LogEvent;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;

import java.io.*;

/**
 * This class is only for simplification other ndc packager class
 */
public abstract class NdcPackager implements LogSource, ISOPackager {
    protected Logger logger = null;
    protected String realm = null;

    private ByteArrayOutputStream out;
    private PrintStream p;

    // protected NdcMsg ndcMsg;

    @Override
    public ISOMsg createISOMsg() {
        return createNdcMsg();
    }

    protected NdcPackager() {
        out = new ByteArrayOutputStream();
        try {
            p = new PrintStream(out, false, "US-ASCII");
        } catch (UnsupportedEncodingException ignored) {
            // utf-8 is a supported encoding
        }
    }

    @Override
    public void setLogger(Logger logger, String realm) {
        this.logger = logger;
        this.realm = realm;
    }

    @Override
    public String getRealm() {
        return realm;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    public abstract byte[] pack();

    public abstract NdcMsg unpack(InputStream in) throws IOException;

    @Override
    public int unpack(ISOComponent m, byte[] b) throws ISOException {
        try {
            unpack(m, new ByteArrayInputStream(b));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ISOException(e);
        }
        return ((ISOMsg) m).getHeader().length + b.length;
    }

    @Override
    public void unpack(ISOComponent m, InputStream in) throws IOException, ISOException {
        LogEvent evt = logger != null ? new LogEvent(this, "unpack") : null;
        try {
            m = unpack(in);
        } finally {
            if (evt != null)
                Logger.log(evt);
        }
    }

    protected abstract NdcMsg createNdcMsg();

    public String readSymbol(InputStream in, ControlCharacter c) {
        StringBuilder buffer = new StringBuilder();

        int symbol;
        while ((symbol = ((ByteArrayInputStream) in).read()) != c.getValue()) {
            buffer.append(Character.toString((char) symbol));
        }

        return buffer.toString();
    }

    /*

    @Override
    protected List<Function<ByteArrayInputStream, BasePackager>> getSupplierList() {
        List<Function<ByteArrayInputStream, BasePackager>> functions = new ArrayList<>();
        functions.add(this::readSTX);
        functions.add(this::readMessageIdentifier);
        return functions;
    }

    protected BasePackager readMessageIdentifier(ByteArrayInputStream in) {
        String symbol = readSymbol(in, ControlCharacter.FS);
        NdcMessageIdentifier messageIdentifier = NdcMessageIdentifier.getInstance(symbol);
        ndcMsg.setMessageIdentifier(messageIdentifier);

        try {
            BasePackager packager = null;
            switch (messageIdentifier) {
                case TRANSACTION_REQUEST:
                    packager = new TransactionRequestPackager(getNdcMsg());
                    break;
                case UNSOLICITED_STATUS:
                    packager = new UnsolicitedMessagePackager(getNdcMsg());
                    break;
                case SOLICITED_STATUS:
                    packager = new SolicitedMessagePackager(getNdcMsg());
                    break;
            }
            packager.getNdcMsg().setPackager(packager);
            ndcMsg.unpack(in);
        } catch (ISOException | IOException e) {
            e.printStackTrace();
        }
        return this;
    }
    */

    protected void readSTX(InputStream in) throws IOException {
        if (ControlCharacter.getInstance((byte) in.read()) != ControlCharacter.STX)
            throw new NdcException("Error unpacking ndc message");
    }

    protected void unpackFS(InputStream in) throws IOException {
        // field separator (FS)
        if (ControlCharacter.getInstance((byte) in.read()) != ControlCharacter.FS)
            throw new NdcException("Error unpacking ndc message");
    }

    @Override
    public byte[] pack(ISOComponent m) throws ISOException {
        return pack();
    }

    // public abstract NdcMsg getNdcMsg();

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getFieldDescription(ISOComponent m, int fldNumber) {
        return null;
    }


}
