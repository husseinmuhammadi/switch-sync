package com.en.std.message.base;

import com.en.std.definition.ProcessingCode;


/**
 * Created by h.mohammadi on 6/18/2016.
 */
public abstract class AuthorizationBase extends TransactionBase implements IAuthorization {
    protected AuthorizationBase(ProcessingCode processingCode) {
        super(processingCode);
    }
}
