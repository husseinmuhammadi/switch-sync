package com.dpi.financial.ftcom.service.base.atm.ndc;

import com.dpi.financial.ftcom.api.base.atm.ndc.OperationCodeService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.atm.ndc.OperationCodeDao;
import com.dpi.financial.ftcom.model.to.atm.ndc.OperationCode;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.Date;
import java.util.List;

import static com.dpi.financial.ftcom.utility.date.DateUtil.getCurrentDate;

@Stateless
@Local(OperationCodeService.class)
public class OperationCodeServiceImpl extends GeneralServiceImpl<OperationCode>
        implements OperationCodeService {
    Logger logger = LoggerFactory.getLogger(GenericDao.class);


    @EJB
    private OperationCodeDao dao;

    @Override
    public GenericDao<OperationCode> getGenericDao() {
        return dao;
    }

    @Override
    public List<OperationCode> findAllByEffectiveDate(Date effectiveDate) {
        return dao.findAllByEffectiveDate(effectiveDate);
    }

    @Override
    public void createBatch(List<OperationCode> operationCodes) {
        findAllByEffectiveDate(getCurrentDate()).forEach(this::delete);
        logger.info("All operation codes has been deleted.");
        operationCodes.forEach(this::create);
        logger.info("All operation codes added successfully.");
    }
}
