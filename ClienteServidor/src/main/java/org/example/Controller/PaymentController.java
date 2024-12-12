package org.example.Controller;

import org.example.Model.DTO.PaymentDTO;
import org.example.Model.Payment;
import org.example.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
public class PaymentController {

    @Autowired
    private PaymentService pagamentoService;

    @GetMapping
    public List<Payment> listarTodos() {
        return pagamentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> buscarPorId(@PathVariable Long id) {
        return pagamentoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Payment> criar(@RequestBody PaymentDTO pagamentoDTO) {
        try {
            Payment pagamentoEfetuado = pagamentoService.salvar(pagamentoDTO);
            return ResponseEntity.ok(pagamentoEfetuado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}