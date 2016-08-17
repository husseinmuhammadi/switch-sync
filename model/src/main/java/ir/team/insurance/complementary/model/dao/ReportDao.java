package ir.team.insurance.complementary.model.dao;

import ir.team.insurance.complementary.model.base.GenericDao;
import ir.team.insurance.complementary.model.to.Report;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

@Stateless
public class ReportDao extends GenericDao<Report> {

    public ReportDao() {
        super(Report.class);
    }

    public Report findByCode(String code) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("code", code);
        TypedQuery<Report> typedQuery = createNamedQuery(Report.FIND_BY_CODE, parameters);
        return typedQuery.getSingleResult();
    }
}
