package com.dpi.financial.ftcom.web.controller.base.simulator.std.message.base;



import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.MessageDetails;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.TransactionState;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import java.util.Set;

public interface ITransaction {
    Set<TransactionState> getStates();

    ISOMsg getISOMsg();

    void setISOMsg(ISOMsg isoMsg);

    boolean isRequest() throws ISOException;

    ITransaction createResponse() throws ISOException;

    ISOMsg createRequest(MessageDetails messageDetails) throws Exception;
}
