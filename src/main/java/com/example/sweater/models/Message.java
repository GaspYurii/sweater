package com.example.spring.models;


import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.GenerationType.AUTO;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }
}
