package com.dpi.financial.ftcom.model.to.isc.transaction;

import javax.persistence.*;

/**
 * https://en.wikibooks.org/wiki/Java_Persistence/Inheritance
 */

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "NETWORK_MESSAGE_SEQ")
@Inheritance
@DiscriminatorColumn(name="NETWORK_MESSAGE_TYPE", discriminatorType = DiscriminatorType.STRING, length = 3)
// @ForceDiscriminator
@Table(name = "NETWORK_MESSAGE")
@NamedQueries({
        @NamedQuery(name = Network.FIND_ALL, query = "select t from Network t where t.deleted = false")
})
public class Network extends Transaction {
    protected static final String FIND_ALL = "Network.findAll";
}
