package com.rewardapp.backend.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("recycling_centers_materials")
@Data
public class RecyclingCenterMaterial {
    @Id
    @Column("id")
    private Long id;

    //@JoinColumn(name = "material_id")
    private Material material;

    //    @ManyToOne
//    @JoinColumn(name = "recycling_center_id")
    private RecyclingCenter recyclingCenter;
}