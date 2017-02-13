package com.dpi.financial.ftcom.web.controller.base.simulator.message.base;



import com.dpi.financial.ftcom.web.controller.base.simulator.std.message.IFundTransfer;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.ProcessingCode;

public abstract class FundTransferBase extends FinancialBase implements IFundTransfer {
	public FundTransferBase(ProcessingCode processingCode) {
		super(processingCode);
	}
}
