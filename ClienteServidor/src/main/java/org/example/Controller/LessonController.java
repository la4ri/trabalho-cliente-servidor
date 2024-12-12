package org.example.Controller;

import org.example.Model.DTO.LessonDTO;
import org.example.Model.Lesson;
import org.example.Service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aulas")
public class LessonController {

    @Autowired
    private LessonService aulaService;

    @GetMapping
    public List<LessonDTO> listarTodos() {
        return aulaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> buscarPorId(@PathVariable Long id) {
        return aulaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Lesson> criar(@RequestBody LessonDTO lessonDTO) {
        Lesson novaAula = aulaService.salvar(lessonDTO);
        return ResponseEntity.ok(novaAula);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Lesson> atualizar(@PathVariable Long id, @RequestBody LessonDTO lessonDTO) {
        try {
            Lesson lessonAtualizada = aulaService.atualizar(id, lessonDTO);
            return ResponseEntity.ok(lessonAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (aulaService.buscarPorId(id).isPresent()) {
            aulaService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
