package com.dpi.financial.ftcom.web.controller.iso.transaction.issuer;

import com.dpi.financial.ftcom.web.controller.iso.base.issuer.IssuerTransactionBase;
import com.en.std.message.BalanceEnquiry;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class BalanceInquiryBean extends IssuerTransactionBase implements Serializable {

    @Override
    public void request() {

    }
}
