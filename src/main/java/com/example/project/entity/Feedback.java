package com.example.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "authorId")
    private Long authorId;  // Id-ul celui care a scris feedback-ul

    @Column(name = "rating")
    private int rating;  // Valoare între 1 și 5, de exemplu

    @Column(name = "message")
    private String message;  // Conținutul feedback-ului


}
