package com.rewardapp.backend.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;
import java.time.LocalDate;

@Table("users")
@Data
public class User {
    @Id
    @Column("id")
    protected Long id;
    @Column("username")
    protected String username;
    @Column("email")
    protected String email;
    @Column("verified")
    protected Boolean verified = false;
    @Column("register_date")
    protected Date register_date = Date.valueOf(LocalDate.now());
    @Column("type")
    protected UserType type = UserType.USER;

    public enum UserType {
        ADMIN, USER;
    }
}
