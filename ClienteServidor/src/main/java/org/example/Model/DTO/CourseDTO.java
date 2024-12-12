package org.example.Model.DTO;

import lombok.Data;

import java.util.Set;

@Data
public class CourseDTO {
    private Long id;
    private String nomeCurso;
    private String descricao;
    private Long usuarioId;
    private Long categoriaId;
    private Double preco;
    private Set<Long> aulas;
}
