package com.dpi.financial.ftcom.model.to.isc.transaction.network;

import com.dpi.financial.ftcom.model.to.isc.transaction.NetworkRequest;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * https://en.wikibooks.org/wiki/Java_Persistence/Inheritance
 */
@Entity
@DiscriminatorValue("201")
@NamedQueries({
        @NamedQuery(name = CutoverRequest.FIND_ALL, query = "select t from CutoverRequest t where t.deleted = false")
})
public class CutoverRequest extends Cutover implements NetworkRequest {
    public static final String FIND_ALL = "CutoverRequest.findAll";
}
