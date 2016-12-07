package com.dpi.financial.ftcom.service.base.atm;

import com.dpi.financial.ftcom.api.base.atm.TerminalService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.atm.TerminalDao;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;
import org.bouncycastle.bcpg.sig.RevocationReasonTags;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(TerminalService.class)
public class TerminalServiceImpl extends GeneralServiceImpl<Terminal>
        implements TerminalService {

    @EJB
    private TerminalDao dao;

    @Override
    public GenericDao<Terminal> getGenericDao() {
        return dao;
    }

    @Override
    public Terminal findByLuno(String luno) {
        if (luno == null)
            return null;

        return dao.findByLuno(luno);
    }
}
