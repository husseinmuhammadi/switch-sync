package com.dpi.financial.ftcom.web.security.user;

import com.dpi.financial.ftcom.web.bundle.ResourceManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
@ViewScoped
public class LoginController implements Serializable {

    @Inject
    private UserInformation userInformation;

    private String username;
    private String password;
    private boolean rememberMe;

    @PostConstruct
    private void init() {
        logout();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String submit() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date expireDate = simpleDateFormat.parse("2017-03-05");
            if (new Date().compareTo(expireDate) > 0) {
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username,
                password);

        token.setRememberMe(rememberMe);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
            return "/index?faces-redirect=true";
        } catch (AuthenticationException e) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
        }
        return null;
    }

    public void logout() {
        boolean logout = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("logout");
        if (logout) {
            if (SecurityUtils.getSubject().isAuthenticated()) {
                SecurityUtils.getSubject().logout();
                String logoutMessage = ResourceManager.getMessageBundle().getString("logout.message");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, logoutMessage, ""));
            }
        }
    }
}
