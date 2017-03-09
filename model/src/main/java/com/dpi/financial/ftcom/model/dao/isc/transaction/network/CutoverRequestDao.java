package com.dpi.financial.ftcom.model.dao.isc.transaction.network;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.isc.transaction.SwitchTransaction;
import com.dpi.financial.ftcom.model.to.isc.transaction.network.CutoverRequest;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class CutoverRequestDao extends GenericDao<CutoverRequest> {
    public CutoverRequestDao() {
        super(CutoverRequest.class);
    }

    public List<CutoverRequest> findAll() {
        return createNamedQuery(CutoverRequest.FIND_ALL).getResultList();
    }
}
