package com.epam.entities;

public class User {

    private Long id_user;
    private String login;
    private String password;
    private String role;

    public User(Long id_user, String login, String password, String role) {
        this.id_user = id_user;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Long getId_user() {
        return id_user;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id_user=" + id_user +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
