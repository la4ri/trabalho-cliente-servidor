package org.example.Service;

import org.example.Model.Course;
import org.example.Model.DTO.CourseDTO;
import org.example.Model.DTO.LessonDTO;
import org.example.Model.Lesson;
import org.example.Repository.CourseRepository;
import org.example.Repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LessonService {

    @Autowired
    private LessonRepository aulasRepository;

    public List<LessonDTO> listarTodos() {
        List<Lesson> lessons = aulasRepository.findAll();
        return lessons.stream().map(this::mapearParaDTO).toList();
    }

    public Optional<LessonDTO> buscarPorId(Long id) {
        return aulasRepository.findById(id).map(this::mapearParaDTO);
    }

    public Lesson salvar(LessonDTO lessonDTO) {
        Lesson lesson = new Lesson();
        lesson.setNomeAula(lessonDTO.getNomeAula());
        lesson.setConteudoAula(lessonDTO.getConteudoAula());
        return aulasRepository.save(lesson);
    }

    public Lesson atualizar(Long id, LessonDTO lessonDTO) {
        Lesson lessonExistente = aulasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aula não encontrada com o ID: " + id));

        if (lessonDTO.getNomeAula() != null) {
            lessonExistente.setNomeAula(lessonDTO.getNomeAula());
        }
        if (lessonDTO.getConteudoAula() != null) {
            lessonExistente.setConteudoAula(lessonDTO.getConteudoAula());
        }

        return aulasRepository.save(lessonExistente);
    }

    public void deletar(Long id) {
        aulasRepository.deleteById(id);
    }

    private LessonDTO mapearParaDTO(Lesson lesson) {
        LessonDTO dto = new LessonDTO();
        dto.setId(lesson.getId());
        dto.setNomeAula(lesson.getNomeAula());
        dto.setConteudoAula(lesson.getConteudoAula());
        dto.setCreatedAt(lesson.getCreatedAt());
        dto.setUpdatedAt(lesson.getUpdatedAt());
        return dto;
    }
}





