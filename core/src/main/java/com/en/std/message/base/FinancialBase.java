package com.en.std.message.base;

import org.jpos.iso.ISOException;

import com.en.std.definition.ProcessingCode;
import com.en.std.exception.InvalidOperationException;
import com.en.std.message.FundTransferCr;


/**
 * Created by h.mohammadi on 6/18/2016.
 */
public abstract class FinancialBase extends TransactionBase implements IFinancial {
    protected FinancialBase(ProcessingCode processingCode) {
        super(processingCode);
    }

    @Override
    public ITransaction createResponse() throws ISOException {
        if (!isRequest())
            throw new InvalidOperationException("Failed to create response");

        return this;
    }
}
