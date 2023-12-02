package com.rewardapp.backend.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "email_tokens")
public class EmailToken {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "email_tokens_id_seq")
    private Long id;
    @Column(name = "token", nullable = false, unique = true)
    private String token = UUID.randomUUID().toString();
    @Column(name = "expiration_date")
    private Date expirationDate = Date.valueOf(LocalDate.now().plusDays(30L));
    @Column(name = "user_id", nullable = false)
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailToken that = (EmailToken) o;
        return Objects.equals(id, that.id) && Objects.equals(token, that.token) && Objects.equals(expirationDate, that.expirationDate) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, expirationDate, userId);
    }
}
