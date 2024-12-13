package org.example.Model.DTO;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String nome;
    private String email;
    private String ocupacao;
    private Set<Long> cursoIds; // IDs dos cursos associados
}

