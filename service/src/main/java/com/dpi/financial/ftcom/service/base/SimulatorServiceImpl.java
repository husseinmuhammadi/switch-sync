package com.dpi.financial.ftcom.service.base;

import com.dpi.financial.ftcom.api.base.SimulatorService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.SimulatorDao;
import com.dpi.financial.ftcom.model.to.Simulator;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.text.MessageFormat;

@Stateless
@Local(SimulatorService.class)
public class SimulatorServiceImpl extends GeneralServiceImpl<Simulator>
        implements SimulatorService {

    Logger logger = LoggerFactory.getLogger(SimulatorServiceImpl.class);

    @EJB
    private SimulatorDao dao;

    @Override
    public GenericDao<Simulator> getGenericDao() {
        return dao;
    }

    @Override
    public Simulator finByRrn(String rrn) {
        if (rrn == null)
            return null;

        logger.info(MessageFormat.format("Find simulator: {0}", rrn));
        return dao.findByRrn(rrn);
    }
}
