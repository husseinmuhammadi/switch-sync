package com.en.std.message.reversal;

import com.en.std.message.base.ITransaction;


/**
 * Created by h.mohammadi on 6/14/2016.
 */
public interface IReversalTransaction {

    ITransaction getOriginalMessage();
    // private final ITransaction original;


}
