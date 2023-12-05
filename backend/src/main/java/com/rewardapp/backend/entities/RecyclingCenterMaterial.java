package com.rewardapp.backend.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("recycling_centers_materials")
@Data
public class RecyclingCenterMaterial {
    @Id
    @Column("id")
    private Long id;

    //@JoinColumn(name = "material_id")
//    @Transient
//    private Material material;

    //    @ManyToOne
//    @JoinColumn(name = "recycling_center_id")
    @Transient
    private RecyclingCenter recyclingCenter;

    @Column("recycling_center_id")
    private AggregateReference<RecyclingCenter, Long> recyclingCenterId;

    @MappedCollection(idColumn = "id", keyColumn = "")
    private Material material;
}