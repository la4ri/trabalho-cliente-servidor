package org.example.Controller;


import org.example.Model.Enrollment;
import org.example.Service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matricula")
public class EnrollmentController {

    @Autowired
    private EnrollmentService matriculaService;

    @GetMapping
    public List<Enrollment> listarTodos() {
        return matriculaService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Enrollment> criar(@RequestParam Long usuario_id, @RequestParam Long curso_id) {
        try {
            Enrollment matricula = matriculaService.salvar(usuario_id, curso_id);
            return ResponseEntity.ok(matricula);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (matriculaService.buscarPorId(id).isPresent()) {
            matriculaService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}