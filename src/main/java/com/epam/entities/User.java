package com.epam.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id_user;
    private String username;
    private String password;
    @Transient
    private String passwordForConfirmation;
    private String role;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_user", nullable = false)
    private List<Choice> choices = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", nullable = false)
    private List<Subscription> subscriptions = new ArrayList<>();

    public User() {    }

    public User(Long id_user, String username, String password, String role) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId_user() {
        return id_user;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getPasswordForConfirmation() { return passwordForConfirmation; }

    public String getRole() { return role; }

    public void setId_user(Long id_user) { this.id_user = id_user; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public void setPasswordForConfirmation(String pwd) { this.passwordForConfirmation = pwd; }

    public void setRole(String role) { this.role = role; }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(this.role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id_user=" + id_user +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passwordForConfirmation='" + passwordForConfirmation + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
