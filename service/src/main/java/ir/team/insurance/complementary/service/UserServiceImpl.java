package ir.team.insurance.complementary.service;

import ir.team.insurance.complementary.api.UserService;
import ir.team.insurance.complementary.model.dao.UserDao;
import ir.team.insurance.complementary.model.to.User;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(UserService.class)
public class UserServiceImpl implements UserService {

    @EJB
    private UserDao userDao;

    @Override
    public User findUserById(Long id) {
        return userDao.findById(id);
    }
}
