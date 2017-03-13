package com.dpi.financial.ftcom.web.controller.base.simulator.message.base;


import com.dpi.financial.ftcom.web.controller.base.simulator.std.message.base.ITransaction;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.ProcessingCode;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.message.base.TransactionBase;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.jpos.iso.ISOException;


/**
 * Created by h.mohammadi on 6/18/2016.
 */
@Deprecated
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


    public ITransaction createRequest() throws ISOException {
        if (!isRequest())
            throw new InvalidOperationException("Failed to create request");

        return this;
    }


}
