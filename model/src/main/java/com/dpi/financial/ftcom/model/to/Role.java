package com.dpi.financial.ftcom.model.to;

import ir.team.insurance.complementary.model.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Security_Role")
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "Security_Role_SEQ")
@NamedQueries({
        @NamedQuery(name = Role.FIND_ALL,
                query = "select m from Role m")
})
public class Role extends BaseEntity {

    public static final String FIND_ALL = "Role.findAll";

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
