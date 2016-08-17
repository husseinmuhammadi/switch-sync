package ir.team.insurance.complementary.model.to;

import ir.team.insurance.complementary.model.base.BaseEntity;
import ir.team.insurance.complementary.model.converter.LocaleConverter;
import ir.team.insurance.complementary.model.type.LocaleType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Security_User")
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "Security_User_SEQ")
@NamedQueries({
        @NamedQuery(name = User.FIND_ALL_USER,
                query = "select m from User m"),
        @NamedQuery(name = User.FIND_BY_USERNAME_AND_PASSWORD,
                query = "select u from User u where u.username = :username and u.password = :password")
})
public class User extends BaseEntity implements Serializable {

    public static final String FIND_ALL_USER = "User.findAll";

    public static final String FIND_BY_USERNAME_AND_PASSWORD = "User.findByUsernameAndPassword";

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 256)
    private String password;

    @Column(nullable = false, length = 50, name = "First_Name")
    private String fName;

    @Column(nullable = false, length = 50, name = "Last_Name")
    private String lName;

    @Column(nullable = false)
    private String salt;

    @Column(name = "Default_Locale")
    @Convert(converter = LocaleConverter.class)
    private LocaleType localeType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "Date", nullable = false)
    private Date lastLogin;

    @Column(name = "Try_Count_To_Lock_Account", nullable = false)
    private Short tryCountToLockAccount;

    @Column(length = 1, name = "LOCK_USER")
    private boolean lockUser;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "User_Role_Security", joinColumns = {@JoinColumn(name = "User_Id", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "Role_Id", referencedColumnName = "Id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"User_Id", "Role_Id"})})
    private List<Role> roles;

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

    public LocaleType getLocaleType() {
        return localeType;
    }

    public void setLocaleType(LocaleType localeType) {
        this.localeType = localeType;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public Short getTryCountToLockAccount() {
        return tryCountToLockAccount;
    }

    public void setTryCountToLockAccount(Short tryCountToLockAccount) {
        this.tryCountToLockAccount = tryCountToLockAccount;
    }

    public boolean isLockUser() {
        return lockUser;
    }

    public void setLockUser(boolean lock) {
        this.lockUser = lock;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}

