package com.dpi.financial.ftcom.web.controller.base.simulator.std.message.base;





import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.TransactionState;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.ProcessingCode;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by h.mohammadi on 6/14/2016.
 */
public abstract class TransactionBase extends ISOMessage implements ITransaction {

    protected static final String NEW_LINE = System.getProperty("line.separator");
    // private final ProcessPattern processPattern;
    protected final ProcessingCode processingCode;
    private final Set<TransactionState> transactionStates;

    // Data Field 43 (1-23 address 24-36 city 37-38 state 39-40 country)
    private String cardAcceptorNameLocation;

    public TransactionBase(ProcessingCode processingCode) {
        // this.processPattern = processPattern;
        this.processingCode = processingCode;
        transactionStates = EnumSet.noneOf(TransactionState.class);
    }

    public String getCardAcceptorNameLocation() {
        return cardAcceptorNameLocation;
    }

    public void setCardAcceptorNameLocation(String cardAcceptorNameLocation) {
        this.cardAcceptorNameLocation = cardAcceptorNameLocation;
    }

    @Override
    public Set<TransactionState> getStates() {
        return transactionStates;
    }

    @Override
    public String toString() {
        if (processingCode != null)
            return processingCode.toString();
        return "Unknown Transaction";
    }

    @Override
    public ISOMsg getISOMsg() {
        return isoMsg;
    }

    @Override
    public void setISOMsg(ISOMsg isoMsg) {
        this.isoMsg = (ISOMsg)isoMsg.clone();
    }

    @Override
    public boolean isRequest() throws ISOException {
        return isoMsg.isRequest();
    }
}
