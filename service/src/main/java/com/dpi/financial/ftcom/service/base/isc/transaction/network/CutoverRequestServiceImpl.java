package com.dpi.financial.ftcom.service.base.isc.transaction.network;

import com.dpi.financial.ftcom.api.base.isc.transaction.network.CutoverRequestService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.isc.transaction.network.CutoverRequestDao;
import com.dpi.financial.ftcom.model.to.isc.transaction.network.CutoverRequest;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(CutoverRequestService.class)
public class CutoverRequestServiceImpl extends GeneralServiceImpl<CutoverRequest>
        implements CutoverRequestService {
    @EJB
    private CutoverRequestDao dao;

    @Override
    public GenericDao<CutoverRequest> getGenericDao() {
        return dao;
    }
}
