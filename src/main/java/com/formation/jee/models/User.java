package com.formation.jee.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "users")
public class User implements Principal, Serializable {
    private static final long serialVersionUID = 6545177257835959534L;

    @Id
    private String username;

    private String password;

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(
            name = "user_roles",
            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "name"),
            joinColumns = @JoinColumn(name = "username", referencedColumnName = "username")
    )
    private List<Role> roles = new ArrayList<>();

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

    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Checks if the current user has the specified role.
     *
     * @param roleName The name of the role to check.
     * @return {@code true} if the user has the specified role, {@code false} otherwise.
     */
    public boolean hasRole(String roleName) {
        return getRoles().stream().anyMatch(role -> roleName.equals(role.getName()));
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }
}
