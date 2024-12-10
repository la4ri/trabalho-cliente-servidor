package org.example.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
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

    @PrePersist
    public void generateValidationData() {
        // Gera o validationCode caso não exista
        if (this.validationCode == null || this.validationCode.isEmpty()) {
            this.validationCode = UUID.randomUUID().toString();
        }
        // Gera a URL com base no validationCode
        if (this.certificadoUrl == null || this.certificadoUrl.isEmpty()) {
            this.certificadoUrl = "https://clienteservidor.com.br/" + this.validationCode;
        }
    }

    // Getters and Setters criados automaticamente
}

