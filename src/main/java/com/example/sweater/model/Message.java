package com.example.sweater.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Data
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Please fill the message")
    @Length(max = 2048, message = "Message too long (more than 2kb")
    private String text;
    @Length(max = 255, message = "Tag too long (more than 255")
    @NotBlank(message = "Please fill the tag")
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
