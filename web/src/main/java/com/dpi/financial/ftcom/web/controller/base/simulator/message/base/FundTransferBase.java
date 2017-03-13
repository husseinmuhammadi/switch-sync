package com.dpi.financial.ftcom.web.controller.base.simulator.message.base;

import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.ProcessingCode;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.message.IFundTransfer;

@Deprecated
public abstract class FundTransferBase extends FinancialBase implements IFundTransfer {
    public FundTransferBase(ProcessingCode processingCode) {
        super(processingCode);
    }
}
