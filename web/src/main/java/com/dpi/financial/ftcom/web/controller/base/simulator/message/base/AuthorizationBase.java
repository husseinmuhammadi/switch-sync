package com.dpi.financial.ftcom.web.controller.base.simulator.message.base;


import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.ProcessingCode;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.message.base.TransactionBase;

/**
 * Created by h.mohammadi on 6/18/2016.
 */
@Deprecated
public abstract class AuthorizationBase extends TransactionBase implements IAuthorization {
    protected AuthorizationBase(ProcessingCode processingCode) {
        super(processingCode);
    }
}
