package com.rewardapp.backend.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "contributions")
public class Contribution {
    private enum MeasurmentType {
        PIECE, KG;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "contributions_id_seq")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "recycling_center_id")
    private Long recyclingCenterId;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @Column(name = "timestamp")
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "measurment")
    @Enumerated(EnumType.STRING)
    private MeasurmentType measurment;

    @Column(name = "reward")
    private Long reward;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRecyclingCenterId() {
        return recyclingCenterId;
    }

    public void setRecyclingCenterId(Long recyclingCenterId) {
        this.recyclingCenterId = recyclingCenterId;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public MeasurmentType getMeasurment() {
        return measurment;
    }

    public void setMeasurment(MeasurmentType measurment) {
        this.measurment = measurment;
    }

    public Long getReward() {
        return reward;
    }

    public void setReward(Long reward) {
        this.reward = reward;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contribution that = (Contribution) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(recyclingCenterId, that.recyclingCenterId) && Objects.equals(material, that.material) && Objects.equals(timestamp, that.timestamp) && Objects.equals(quantity, that.quantity) && measurment == that.measurment && Objects.equals(reward, that.reward);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, recyclingCenterId, material, timestamp, quantity, measurment, reward);
    }
}