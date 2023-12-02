package com.rewardapp.backend.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "internal_users")
public class InternalUser {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "password")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InternalUser that = (InternalUser) o;
        return Objects.equals(id, that.id) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password);
    }

    @Override
    public String toString() {
        return "InternalUser{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
