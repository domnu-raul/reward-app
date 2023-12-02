package com.rewardapp.backend.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "contributions")
public class UserContributions {
    public static enum MeasurmentType {
        PIECE,
        KG,
        G
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "contributions_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserData userData;

    @Column(name = "recycling_center_id", nullable = false)
    private Long recyclingCenterId;

    @Column(name = "timestamp", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private Timestamp timestamp;

    @OneToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "measurment", nullable = false)
    @Enumerated(EnumType.STRING)
    private MeasurmentType measurment;


    public MeasurmentType getMeasurment() {
        return measurment;
    }

    public void setMeasurment(MeasurmentType measurment) {
        this.measurment = measurment;
    }

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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
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
        return quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(userData, that.userData) && Objects.equals(recyclingCenterId, that.recyclingCenterId) && Objects.equals(timestamp, that.timestamp) && material == that.material && measurment == that.measurment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userData, recyclingCenterId, timestamp, material, quantity, measurment);
    }
}