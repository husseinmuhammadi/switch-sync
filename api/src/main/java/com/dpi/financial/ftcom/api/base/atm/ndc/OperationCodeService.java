package com.dpi.financial.ftcom.api.base.atm.ndc;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.to.atm.ndc.OperationCode;

import java.util.Date;
import java.util.List;

public interface OperationCodeService extends GeneralServiceApi<OperationCode> {
    List<OperationCode> findAllByEffectiveDate(Date effectiveDate);

    void createBatch(List<OperationCode> operationCodes);
}
