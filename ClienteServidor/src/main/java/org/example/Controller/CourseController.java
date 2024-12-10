package org.example.Controller;

import org.example.Model.Course;
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
    public Course criar(@RequestBody Course curso) {
        return cursoService.salvar(curso);
    }

    @PostMapping("/{id}/aulas")
    public ResponseEntity<Course> adicionarAulas(@PathVariable Long id, @RequestBody Set<Long> lessonIds) {
        Course course = cursoService.adicionarAula(id, lessonIds);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> atualizar(@PathVariable Long id, @RequestBody Course cursoAtualizado) {
        return cursoService.buscarPorId(id)
                .map(cursoExistente -> {
                    cursoExistente.setNomeCurso(cursoAtualizado.getNomeCurso());
                    cursoExistente.setDescricao(cursoAtualizado.getDescricao());
                    cursoExistente.setProfessor(cursoAtualizado.getProfessor());
                    cursoExistente.setCategoria(cursoAtualizado.getCategoria());
                    cursoExistente.setPreco(cursoAtualizado.getPreco());
                    return ResponseEntity.ok(cursoService.salvar(cursoExistente));
                })
                .orElse(ResponseEntity.notFound().build());
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