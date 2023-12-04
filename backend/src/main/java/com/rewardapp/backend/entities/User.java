package com.rewardapp.backend.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    public enum UserType {
        ADMIN, USER;
    };

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "users_id_seq")
    protected Long id;
    @Column(name = "username", nullable = false, unique = true)
    protected String username;
    @Column(name = "email", nullable = false, unique = true)
    protected String email;
    @Column(name = "verified", columnDefinition = "BOOLEAN DEFAULT FALSE")
    protected Boolean verified = false;
    @Column(name = "register_date", columnDefinition = "DATE DEFAULT now()")
    protected Date register_date = Date.valueOf(LocalDate.now());

    @Column(name = "type", columnDefinition = "USER_TYPE DEFAULT 'user'::user_type")
    @Enumerated(EnumType.STRING)
    protected UserType type = UserType.USER;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(verified, user.verified) && Objects.equals(register_date, user.register_date) && type == user.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, verified, register_date, type);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", verified=" + verified +
                ", register_date=" + register_date +
                ", type=" + type +
                '}';
    }
}
