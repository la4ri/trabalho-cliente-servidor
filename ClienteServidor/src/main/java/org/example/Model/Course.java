package org.example.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cursos")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCurso;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private User professor;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Category categoria;

    private Double preco;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
