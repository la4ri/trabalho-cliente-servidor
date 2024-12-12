package org.example.Model.DTO;

import lombok.Data;

@Data
public class CertificateDTO {
    private Long usuarioId;
    private Long cursoId;
    private String certificadoUrl;
}

