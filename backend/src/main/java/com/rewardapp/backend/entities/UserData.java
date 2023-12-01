package com.rewardapp.backend.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserData {
    @Id
    @Column(name = "id")
    private Long userId;

    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL)
    private List<UserContributions> contributions;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<UserContributions> getContributions() {
        return contributions;
    }

    public void setContributions(List<UserContributions> contributions) {
        this.contributions = contributions;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return Objects.equals(userId, userData.userId) && Objects.equals(contributions, userData.contributions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, contributions);
    }
}
