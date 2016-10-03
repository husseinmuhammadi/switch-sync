package com.en.std.message;

import com.en.std.definition.ProcessingCode;
import com.en.std.message.base.FinancialBase;


public class PinChange extends FinancialBase {

    public PinChange() {
        super(ProcessingCode.PinChange);
    }
}

