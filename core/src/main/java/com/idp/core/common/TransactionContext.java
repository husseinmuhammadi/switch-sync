package com.idp.core.common;

import org.jpos.transaction.Context;

/**
 * Created by h.mohammadi on 8/16/2016.
 */
public class TransactionContext extends Context {

    long expires;

    public TransactionContext () {
        super();
    }

    public TransactionContext (long expires) {
        super();
        this.expires = System.currentTimeMillis() + expires;
    }

    public boolean isExpired () {
        return expires < System.currentTimeMillis ();
    }
}
