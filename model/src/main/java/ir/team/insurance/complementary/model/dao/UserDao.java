package ir.team.insurance.complementary.model.dao;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.base.QueryParameterUtil;
import com.dpi.financial.ftcom.model.to.User;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class UserDao extends GenericDao<User> {

    public UserDao() {
        super(User.class);
    }

    public List<User> findAll() {
        TypedQuery<User> userTypedQuery = createNamedQuery(User.FIND_ALL_USER);
        return userTypedQuery.getResultList();
    }

    /**
     * @throws NoResultException if there is no result
     */
    public User login(String username, String password) {
        TypedQuery<User> typedQuery = createNamedQuery(User.FIND_BY_USERNAME_AND_PASSWORD,
                QueryParameterUtil.with("username", username).and("password", password).parameters());
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
