package org.example.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "aulas")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeAula;
    private String conteudoAula;

    @Column(name = "order_index")
    private Integer order;

    @ManyToMany(mappedBy = "aulas")
    private Set<Course> cursos;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Getters and Setters criados automaticamente
}

