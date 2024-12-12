package org.example.Controller;


import org.example.Model.Certificate;
import org.example.Model.Course;
import org.example.Model.DTO.CertificateDTO;
import org.example.Model.User;
import org.example.Repository.CourseRepository;
import org.example.Repository.UserRepository;
import org.example.Service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificado")
public class CertificateController {

    @Autowired
    private CertificateService certificadoService;

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private CourseRepository cursoRepository;

    @GetMapping
    public List<Certificate> listarTodos() {
        return certificadoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Certificate> buscarPorId(@PathVariable Long id) {
        return certificadoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Certificate> criar(@RequestBody CertificateDTO certificadoDTO) {
        try {
            Certificate certificadoCriado = certificadoService.salvar(certificadoDTO);
            return ResponseEntity.ok(certificadoCriado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Certificate> atualizar(@PathVariable Long id, @RequestBody CertificateDTO certificadoDTO) {
        try {
            Certificate certificadoAtualizado = certificadoService.atualizar(id, certificadoDTO);
            return ResponseEntity.ok(certificadoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (certificadoService.buscarPorId(id).isPresent()) {
            certificadoService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
