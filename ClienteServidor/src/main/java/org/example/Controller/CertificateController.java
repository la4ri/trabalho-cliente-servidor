package org.example.Controller;


import org.example.Model.Certificate;
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
    public Certificate criar(@RequestBody Certificate certificado) {
        return certificadoService.salvar(certificado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Certificate> atualizar(@PathVariable Long id, @RequestBody Certificate cursoAtualizado) {
        return certificadoService.buscarPorId(id)
                .map(certificadoExistente -> {
                    certificadoExistente.setUsuario(cursoAtualizado.getUsuario());
                    certificadoExistente.setCurso(cursoAtualizado.getCurso());
                    certificadoExistente.setCertificadoUrl(cursoAtualizado.getCertificadoUrl());
                    return ResponseEntity.ok(certificadoService.salvar(certificadoExistente));
                })
                .orElse(ResponseEntity.notFound().build());
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