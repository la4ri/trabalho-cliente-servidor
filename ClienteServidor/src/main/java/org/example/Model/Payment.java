package org.example.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamento")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Course curso;

    private Double amount; //total pago nos cursos

    private String status; //"aprovado" ou "pendente"

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
