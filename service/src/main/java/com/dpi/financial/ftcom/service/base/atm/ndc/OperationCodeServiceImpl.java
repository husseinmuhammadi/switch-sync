package com.dpi.financial.ftcom.service.base.atm.ndc;

import com.dpi.financial.ftcom.api.base.atm.ndc.OperationCodeService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.atm.ndc.OperationCodeDao;
import com.dpi.financial.ftcom.model.to.atm.ndc.OperationCode;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;

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
        operationCodes.forEach(this::create);
    }
}
