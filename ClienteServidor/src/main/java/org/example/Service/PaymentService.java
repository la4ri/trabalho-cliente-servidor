package org.example.Service;

import org.example.Model.Payment;
import org.example.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository pagamentoRepository;

    public List<Payment> listarTodos() {
        return pagamentoRepository.findAll();
    }

    public Optional<Payment> buscarPorId(Long id) {
        return pagamentoRepository.findById(id);
    }

    public Payment salvar(Payment certificado) {
        return pagamentoRepository.save(certificado);
    }

    public void deletar(Long id) {
        pagamentoRepository.deleteById(id);
    }
}