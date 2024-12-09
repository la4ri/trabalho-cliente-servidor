package org.example.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "certificados")
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Course curso;

    @Column(name = "certificado_url")
    private String certificadoUrl;

    @Column(name = "issued_date")
    private LocalDateTime issuedDate = LocalDateTime.now();

    @Column(name = "validation_code", unique = true)
    private String validationCode;
}

