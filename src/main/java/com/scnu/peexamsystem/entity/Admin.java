package com.scnu.peexamsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @Column(name = "admin_id")
    private String adminID;
    @Column(name = "admin_name")
    private String adminName;
    @Column(name = "password")
    private String password;
}
