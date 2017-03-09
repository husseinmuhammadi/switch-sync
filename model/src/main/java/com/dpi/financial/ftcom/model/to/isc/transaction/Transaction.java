package com.dpi.financial.ftcom.model.to.isc.transaction;

import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.converter.ProcessingCodeConverter;
import com.dpi.financial.ftcom.model.type.ProcessingCode;

import javax.persistence.*;

/**
 * https://en.wikibooks.org/wiki/Java_Persistence/Inheritance
 * http://www.gmarwaha.com/blog/2009/08/26/hibernate-why-should-i-force-discriminator/
 */

@MappedSuperclass
public abstract class Transaction extends EntityBase {

    @Column(name = "MTI", nullable = false, length = 4)
    protected String mti;

    public String getMti() {
        return mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }
}
