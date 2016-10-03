package com.en.std.message;


import com.en.std.definition.ProcessingCode;
import com.en.std.message.base.FinancialBase;

public class Purchase extends FinancialBase {

    public Purchase() {
        super(ProcessingCode.Purchase);
    }
}

