package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class Role implements GrantedAuthority {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String roleTitle;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role() {}

    public Role(Integer id) {
        this.id = id;
    }

    public Role(Integer id, String roleTitle) {
        this.id = id;
        this.roleTitle = roleTitle;
    }

    public Role(String roleTitle) {
        this.roleTitle = roleTitle;
    }

    public Role(String roleTitle, List<User> users) {
        this.roleTitle = roleTitle;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getRoleTitle();
    }

    @Override
    public String toString() {
        return roleTitle.split("_")[1];
    }
}
