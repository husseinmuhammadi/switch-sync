package ir.team.insurance.complementary.model.dao;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.Role;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class RoleDao extends GenericDao<Role> {

    public RoleDao() {
        super(Role.class);
    }

    public List<Role> findAll() {
        TypedQuery<Role> roleTypedQuery = createNamedQuery(Role.FIND_ALL);
        return roleTypedQuery.getResultList();
    }
}
