package org.example.Controller;


import org.example.Model.DTO.EnrollmentDTO;
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
    public ResponseEntity<Enrollment> criar(@RequestBody EnrollmentDTO enrollmentDTO) {
        try {
            Enrollment matriculaCriada = matriculaService.salvar(enrollmentDTO);
            return ResponseEntity.ok(matriculaCriada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
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