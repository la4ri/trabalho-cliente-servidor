package org.example.Service;

import org.example.Model.Course;
import org.example.Model.DTO.PaymentDTO;
import org.example.Model.Payment;
import org.example.Model.User;
import org.example.Repository.CourseRepository;
import org.example.Repository.PaymentRepository;
import org.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository pagamentoRepository;

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private CourseRepository cursoRepository;

    public List<Payment> listarTodos() {
        return pagamentoRepository.findAll();
    }

    public Optional<Payment> buscarPorId(Long id) {
        return pagamentoRepository.findById(id);
    }

    public Payment salvar(PaymentDTO pagamentoDTO) {
        // Busca o usuário e o curso pelos IDs fornecidos no DTO
        User usuario = usuarioRepository.findById(pagamentoDTO.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + pagamentoDTO.getUsuarioId()));
        Course curso = cursoRepository.findById(pagamentoDTO.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado com o ID: " + pagamentoDTO.getCursoId()));

        // Cria um novo certificado com os dados do DTO
        Payment pagamento = new Payment();
        pagamento.setUsuarioId(usuario);
        pagamento.setCursoId(curso);
        pagamento.setValorPago(pagamentoDTO.getValorPago());
        pagamento.setStatus(pagamentoDTO.getStatus());

        return pagamentoRepository.save(pagamento);
    }

    public void deletar(Long id) {
        pagamentoRepository.deleteById(id);
    }
}