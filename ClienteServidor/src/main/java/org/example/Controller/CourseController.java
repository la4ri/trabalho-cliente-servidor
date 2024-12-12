package org.example.Controller;

import org.example.Model.Course;
import org.example.Model.DTO.CourseDTO;
import org.example.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/cursos")
public class CourseController {

    @Autowired
    private CourseService cursoService;

    @GetMapping
    public List<Course> listarTodos() {
        return cursoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> buscarPorId(@PathVariable Long id) {
        return cursoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Course> criar(@RequestBody CourseDTO cursoDTO) {
        try {
            Course cursoCriado = cursoService.salvar(cursoDTO);
            return ResponseEntity.ok(cursoCriado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> atualizar(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        try {
            Course cursoAtualizado = cursoService.atualizar(id, courseDTO);
            return ResponseEntity.ok(cursoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (cursoService.buscarPorId(id).isPresent()) {
            cursoService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}