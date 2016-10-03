package com.en.std.message;

import com.en.std.definition.ProcessingCode;
import com.en.std.message.base.FinancialBase;


public class MiniStatement extends FinancialBase {

    public MiniStatement() {
        super(ProcessingCode.MiniStatement);
    }
}

