package org.example.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
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

    @ManyToMany
    @JoinTable(
            name = "curso_aula", // Nome da tabela de junção
            joinColumns = @JoinColumn(name = "curso_id"), // FK para Course
            inverseJoinColumns = @JoinColumn(name = "aula_id") // FK para Lesson
    )
    private Set<Lesson> aulas; // Alterado para evitar confusão

    private Double preco;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Getters and Setters criados automaticamente

}
