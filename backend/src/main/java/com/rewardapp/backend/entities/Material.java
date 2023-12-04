package com.rewardapp.backend.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("materials")
@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public class Material {
    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;
}
