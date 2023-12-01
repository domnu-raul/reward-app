package com.rewardapp.backend.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "recycling_centers_materials")
public class RecyclingCenterMaterials {
    public static enum MaterialType {
        PLASTIC,
        METAL,
        PAPER,
        GLASS,
        ALUMINUM_CANS,
        E_WASTE
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "material", columnDefinition = "material_type")
    @Enumerated(EnumType.STRING)
    private MaterialType material;

    @ManyToOne
    @JoinColumn(name = "recycling_center_id", columnDefinition = "INT FOREIGN KEY REFERENCES recycling_centers(id)")
    private RecyclingCenter recyclingCenter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MaterialType getMaterial() {
        return material;
    }

    public void setMaterial(MaterialType material) {
        this.material = material;
    }

    public void setRecyclingCenter(RecyclingCenter recyclingCenter) {
        this.recyclingCenter = recyclingCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecyclingCenterMaterials that = (RecyclingCenterMaterials) o;
        return Objects.equals(id, that.id) && Objects.equals(recyclingCenter, that.recyclingCenter) && material == that.material;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recyclingCenter, material);
    }
}

