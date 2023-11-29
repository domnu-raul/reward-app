package com.rewardapp.backend.entities;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "recycling_centers")
public class RecyclingCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "materials", columnDefinition = "material_type[]")
    @Enumerated(EnumType.STRING)
    @OneToMany(mappedBy = "recyclingCenter")
    private List<RecyclingCenterMaterials> materials;
    //private LocationType location;
    private Time starting_time;
    private Time end_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RecyclingCenterMaterials.MaterialType> getMaterials() {
        return materials
                .stream()
                .map(center -> center.getMaterial())
                .collect(Collectors.toList());
    }

    public void setMaterials(List<RecyclingCenterMaterials> materials) {
        this.materials = materials;
    }

    public Time getStarting_time() {
        return starting_time;
    }

    public void setStarting_time(Time starting_time) {
        this.starting_time = starting_time;
    }

    public Time getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Time end_time) {
        this.end_time = end_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecyclingCenter that = (RecyclingCenter) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(materials, that.materials) && Objects.equals(starting_time, that.starting_time) && Objects.equals(end_time, that.end_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, materials, starting_time, end_time);
    }
}
