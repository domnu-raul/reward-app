package com.rewardapp.backend.models;

import java.util.Objects;

public class InternalUserModel extends UserModel{
    private String password;
    private String salt;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InternalUserModel that = (InternalUserModel) o;
        return Objects.equals(password, that.password) && Objects.equals(salt, that.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), password, salt);
    }
}
