package com.example.sweater.model;


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

    private String filename;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public Message() {
    }

    public Message(String text, String tag, User author) {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    public String getAuthorName() {
        return author.getUsername();
    }
}
