package ir.team.insurance.complementary.api;


import com.dpi.financial.ftcom.model.to.User;

public interface UserService {
    User findUserById(Long id);
}
