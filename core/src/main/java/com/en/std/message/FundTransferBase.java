package com.en.std.message;

import com.en.std.definition.ProcessingCode;
import com.en.std.message.base.FinancialBase;
import com.en.std.message.base.TransactionBase;

public abstract class FundTransferBase extends FinancialBase implements IFundTransfer {
	public FundTransferBase(ProcessingCode processingCode) {
		super(processingCode);
	}
}
