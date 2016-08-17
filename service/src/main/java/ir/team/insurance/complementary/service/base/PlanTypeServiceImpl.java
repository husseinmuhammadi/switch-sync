package ir.team.insurance.complementary.service.base;

import ir.team.insurance.complementary.api.base.PlanTypeService;
import ir.team.insurance.complementary.model.dao.PlanTypeDao;
import ir.team.insurance.complementary.model.to.PlanType;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(PlanTypeService.class)
public class PlanTypeServiceImpl implements PlanTypeService {

    @EJB
    private PlanTypeDao planTypeDao;

    @Override
    public PlanType create(PlanType planType) {
        return planTypeDao.create(planType);
    }

    @Override
    public List<PlanType> findAll() {
        return planTypeDao.findAll();
    }

    @Override
    public PlanType find(Long id) {
        return planTypeDao.findById(id);
    }

    @Override
    public void delete(PlanType t) {
        planTypeDao.remove(t);
    }

    @Override
    public void update(PlanType t) {
        planTypeDao.update(t);
    }
}
