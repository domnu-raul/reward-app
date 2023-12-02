package com.rewardapp.backend.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "recycling_centers_materials")
public class RecyclingCenterMaterial {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "recycling_center_id")
    private RecyclingCenter recyclingCenter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setRecyclingCenter(RecyclingCenter recyclingCenter) {
        this.recyclingCenter = recyclingCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecyclingCenterMaterial that = (RecyclingCenterMaterial) o;
        return Objects.equals(id, that.id) && Objects.equals(recyclingCenter, that.recyclingCenter) && material == that.material;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recyclingCenter, material);
    }
}

