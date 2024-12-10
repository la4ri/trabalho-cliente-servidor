package org.example.Controller;


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
    public List<Lesson> listarTodos() {
        return aulaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> buscarPorId(@PathVariable Long id) {
        return aulaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Lesson criar(@RequestBody Lesson curso) {
        return aulaService.salvar(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lesson> atualizar(@PathVariable Long id, @RequestBody Lesson aulaAtualizado) {
        return aulaService.buscarPorId(id)
                .map(aulaExistente -> {
                    aulaExistente.setNomeAula(aulaAtualizado.getNomeAula());
                    aulaExistente.setConteudoAula(aulaAtualizado.getConteudoAula());
                    aulaExistente.setCursos(aulaAtualizado.getCursos());
                    return ResponseEntity.ok(aulaService.salvar(aulaExistente));
                })
                .orElse(ResponseEntity.notFound().build());
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