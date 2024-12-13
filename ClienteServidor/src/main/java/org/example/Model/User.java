package org.example.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "usuario")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;

    @ManyToMany
    @JoinTable(
            name = "usuario_curso", // Nome da tabela intermediária
            joinColumns = @JoinColumn(name = "usuario_id"), // FK para User
            inverseJoinColumns = @JoinColumn(name = "curso_id") // FK para Course
    )
    private Set<Course> cursos;

    @Column(name = "ocupacao")
    private String ocupacao; // "estudante", "professor" ou "admin"

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Getters and Setters criados automaticamente
}
