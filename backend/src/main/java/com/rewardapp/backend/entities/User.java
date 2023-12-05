package com.rewardapp.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Table("users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column("id")
    protected Long id;
    @Column("username")
    protected String username;
    @Column("email")
    protected String email;
    @Column("verified")
    protected Boolean verified;
    @Column("register_date")
    protected Date register_date;
    @Column("type")
    protected UserType type;

    public enum UserType {
        ADMIN, USER;
    }
}
