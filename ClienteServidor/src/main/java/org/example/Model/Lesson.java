package org.example.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "aulas")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeAula;
    private String conteudoAula;

    @Column(name = "order_index")
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Course curso;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Getters and Setters
}

