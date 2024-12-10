package org.example.Controller;

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
    public Payment criar(@RequestBody Payment curso) {
        return pagamentoService.salvar(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> atualizar(@PathVariable Long id, @RequestBody Payment pagamentoAtualizado) {
        return pagamentoService.buscarPorId(id)
                .map(pagamentoExistente -> {
                    pagamentoExistente.setUsuario(pagamentoAtualizado.getUsuario());
                    pagamentoExistente.setCurso(pagamentoAtualizado.getCurso());
                    pagamentoExistente.setAmount(pagamentoAtualizado.getAmount());
                    pagamentoExistente.setStatus(pagamentoAtualizado.getStatus());
                    return ResponseEntity.ok(pagamentoService.salvar(pagamentoExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}