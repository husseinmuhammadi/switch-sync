package com.dpi.financial.ftcom.model.dao.atm.ndc;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.atm.ndc.OperationCode;
import com.sleepycat.je.dbi.Operation;

import javax.ejb.Stateless;
import java.util.*;

@Stateless
public class OperationCodeDao extends GenericDao<OperationCode> {
    public OperationCodeDao() {
        super(OperationCode.class);
    }

    public List<OperationCode> findAll() {
        return createNamedQuery(OperationCode.FIND_ALL).getResultList();
    }

    public List<OperationCode> findAllByEffectiveDate(Date currentDate) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("currentDate", currentDate);
        return createNamedQuery(OperationCode.FIND_ALL_BY_EFFECTIVE_DATE, parameters).getResultList();
    }
}
