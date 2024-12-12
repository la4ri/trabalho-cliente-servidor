package org.example.Service;

import org.example.Model.Certificate;
import org.example.Model.Course;
import org.example.Model.DTO.CertificateDTO;
import org.example.Model.User;
import org.example.Repository.CertificateRepository;
import org.example.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CertificateService {

    @Autowired
    private CertificateRepository certificadoRepository;

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private CourseRepository cursoRepository;

    public List<Certificate> listarTodos() {
        return certificadoRepository.findAll();
    }

    public Optional<Certificate> buscarPorId(Long id) {
        return certificadoRepository.findById(id);
    }

    public Certificate salvar(CertificateDTO certificadoDTO) {
        // Busca o usuário e o curso pelos IDs fornecidos no DTO
        User usuario = usuarioRepository.findById(certificadoDTO.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + certificadoDTO.getUsuarioId()));
        Course curso = cursoRepository.findById(certificadoDTO.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado com o ID: " + certificadoDTO.getCursoId()));

        // Cria um novo certificado com os dados do DTO
        Certificate certificado = new Certificate();
        certificado.setUsuarioId(usuario);
        certificado.setCursoId(curso);
        certificado.setCertificadoUrl(certificadoDTO.getCertificadoUrl());

        return certificadoRepository.save(certificado);
    }

    public Certificate atualizar(Long id, CertificateDTO certificadoDTO) {
        // Busca o certificado existente
        Certificate certificadoExistente = certificadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Certificado não encontrado com o ID: " + id));

        // Atualiza os campos com base no DTO
        if (certificadoDTO.getUsuarioId() != null) {
            User usuario = usuarioRepository.findById(certificadoDTO.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + certificadoDTO.getUsuarioId()));
            certificadoExistente.setUsuarioId(usuario);
        }

        if (certificadoDTO.getCursoId() != null) {
            Course curso = cursoRepository.findById(certificadoDTO.getCursoId())
                    .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado com o ID: " + certificadoDTO.getCursoId()));
            certificadoExistente.setCursoId(curso);
        }

        if (certificadoDTO.getCertificadoUrl() != null) {
            certificadoExistente.setCertificadoUrl(certificadoDTO.getCertificadoUrl());
        }

        return certificadoRepository.save(certificadoExistente);
    }


    public void deletar(Long id) {
        certificadoRepository.deleteById(id);
    }
}
