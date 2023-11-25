package com.rewardapp.backend.models;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class UserModel {
    protected Integer id;
    protected String username;
    protected String email;
    protected Boolean verified;
    protected String register_date;
    protected String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(id, userModel.id) && Objects.equals(username, userModel.username) && Objects.equals(email, userModel.email) && Objects.equals(verified, userModel.verified) && Objects.equals(register_date, userModel.register_date) && Objects.equals(type, userModel.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, verified, register_date, type);
    }
}
