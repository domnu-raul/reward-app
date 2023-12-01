package com.rewardapp.backend.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "contributions")
public class UserContributions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserData userData;

    @Column(name = "recycling_center_id", nullable = false)
    private Long recyclingCenterId;

    @Column(name = "timestamp", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private Timestamp timestamp;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RecyclingCenterMaterials.MaterialType material;

    @Column(nullable = false)
    private int quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public UserData getUserData() {
//        return userData;
//    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Long getRecyclingCenterId() {
        return recyclingCenterId;
    }

    public void setRecyclingCenterId(Long recyclingCenterId) {
        this.recyclingCenterId = recyclingCenterId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public RecyclingCenterMaterials.MaterialType getMaterial() {
        return material;
    }

    public void setMaterial(RecyclingCenterMaterials.MaterialType material) {
        this.material = material;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserContributions that = (UserContributions) o;
        return quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(userData, that.userData) && Objects.equals(recyclingCenterId, that.recyclingCenterId) && Objects.equals(timestamp, that.timestamp) && material == that.material;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userData, recyclingCenterId, timestamp, material, quantity);
    }
}