package org.example.Model.DTO;

import lombok.Data;

@Data
public class PaymentDTO {
    private Long usuarioId;
    private Long cursoId;
    private Double valorPago;
    private String status;
}
