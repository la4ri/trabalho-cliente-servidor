package org.example.Model.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LessonDTO {
    private Long id;
    private String nomeAula;
    private String conteudoAula;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
