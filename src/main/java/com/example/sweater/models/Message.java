package com.example.sweater.models;


import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Data
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    @Column(nullable = false)
    private String text;

    private String tag;

    public Message() {
    }

    public Message(String text, String tag) {
        this.text = text;
        this.tag = tag;
    }
}
