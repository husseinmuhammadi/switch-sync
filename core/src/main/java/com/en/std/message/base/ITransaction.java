package com.en.std.message.base;


import com.en.std.definition.TransactionState;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import java.util.Set;

public interface ITransaction {
    Set<TransactionState> getStates();

    ISOMsg getISOMsg();

    void setISOMsg(ISOMsg isoMsg);

    boolean isRequest() throws ISOException;

    ITransaction createResponse() throws ISOException;
}
