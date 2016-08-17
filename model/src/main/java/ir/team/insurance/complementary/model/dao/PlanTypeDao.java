package ir.team.insurance.complementary.model.dao;

import com.dpi.financial.ftcom.model.to.PlanType;
import ir.team.insurance.complementary.model.base.GenericDao;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PlanTypeDao extends GenericDao<PlanType> {

    public PlanTypeDao() {
        super(PlanType.class);
    }

    public List<PlanType> findAll() {
        TypedQuery<PlanType> planTypeTypedQuery = createNamedQuery(PlanType.FIND_ALL);
        return planTypeTypedQuery.getResultList();
    }
}
