package ir.team.insurance.complementary.api;


import ir.team.insurance.complementary.model.to.User;

public interface UserService {
    User findUserById(Long id);
}
