package com.dpi.financial.ftcom.core.atm.iso.ndc.channel;

import com.dpi.financial.ftcom.core.atm.iso.ndc.header.NdcHeader;
import com.dpi.financial.ftcom.core.atm.iso.ndc.msg.NdcMsg;
import com.dpi.financial.ftcom.core.atm.iso.ndc.packager.SolicitedMessagePackager;
import com.dpi.financial.ftcom.core.atm.iso.ndc.packager.TransactionRequestPackager;
import com.dpi.financial.ftcom.core.atm.iso.ndc.packager.UnsolicitedMessagePackager;
import com.dpi.financial.ftcom.model.type.atm.ndc.MessageIdentifier;
import com.dpi.financial.ftcom.utility.encoding.AsciiCharset;
import org.jpos.iso.*;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

/**
 *
 */
public class NdcChannel extends BaseChannel {
    public NdcChannel() {
    }

    public NdcChannel(String host, int port, ISOPackager p) {
        super(host, port, p);
    }

    public NdcChannel(ISOPackager p) throws IOException {
        super(p);
    }

    public NdcChannel(ISOPackager p, ServerSocket serverSocket) throws IOException {
        super(p, serverSocket);
    }

    @Override
    protected int getHeaderLength() {
        // return super.getHeaderLength();
        return 4;
    }

    @Override
    protected byte[] streamReceive() throws IOException {
        int i = 0;
        byte[] buffer = new byte[4096];
        for (i = 0; i < 4096; i++) {
            int c = -1;
            try {
                c = serverIn.read();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            buffer[i] = (byte) c;

            if (c == 03) {
                break;
            } else if (c == -1) {
                throw new EOFException("connection closed");
            }
        }
        if (i == 4096) {
            throw new IOException("packet too long");
        }

        byte[] bytes = new byte[i + 1];
        System.arraycopy(buffer, 0, bytes, 0, i + 1);
        if (bytes.length > 0) {
            System.out.println("------------------------------------------");
            AsciiCharset asciiCharset = new AsciiCharset();
            System.out.println(asciiCharset.decode(bytes));
            System.out.println("------------------------------------------");
            System.out.print("{");
            for (byte b : bytes) {
                // System.out.print(String.format("%c, ", b));
                System.out.print(String.format("%d, ", b));
            }
            System.out.println("}");
            System.out.println("------------------------------------------");
        }

        return bytes;
    }

    @Override
    protected ISOPackager getDynamicPackager(byte[] header, byte[] image) {
        ISOPackager packager = null;
        try {
            switch (MessageIdentifier.getInstance(new String(new byte[]{header[1], header[2]}))) {
                case TRANSACTION_REQUEST:
                    packager = new TransactionRequestPackager();
                    break;
                case UNSOLICITED_STATUS:
                    packager = new UnsolicitedMessagePackager();
                    break;
                case SOLICITED_STATUS:
                    packager = new SolicitedMessagePackager();
                    break;
            }
        } catch (ISOException e) {
            e.printStackTrace();
        }
        return packager;
    }

    @Override
    protected ISOHeader getDynamicHeader(byte[] image) {
        // return super.getDynamicHeader(image);
        if (image != null) {
            NdcHeader header = new NdcHeader();
            header.unpack(image);
            return header;
        }

        return null;
    }

    @Override
    protected ISOPackager getDynamicPackager(ISOMsg m) {
        NdcMsg ndcMsg = (NdcMsg) m;
        return ndcMsg.getPackager();
    }
}
