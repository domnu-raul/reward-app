package com.rewardapp.backend.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("internal_users")
@Data
public class InternalUser {
    @Id
    @Column("id")
    private Long id;
    @Column("password")
    private String password;
}
