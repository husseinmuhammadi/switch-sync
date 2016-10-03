package com.en.std.message.base;

import com.en.std.definition.ProcessingCode;


/**
 * Created by h.mohammadi on 6/18/2016.
 */
public abstract class ReversalBase extends TransactionBase implements IReversal {
    protected ReversalBase(ProcessingCode processingCode) {
        super(processingCode);
    }

}
