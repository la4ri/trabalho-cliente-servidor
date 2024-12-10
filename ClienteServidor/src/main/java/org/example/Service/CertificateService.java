package org.example.Service;

import org.example.Model.Certificate;
import org.example.Repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificateService {

    @Autowired
    private CertificateRepository certificadoRepository;

    public List<Certificate> listarTodos() {
        return certificadoRepository.findAll();
    }

    public Optional<Certificate> buscarPorId(Long id) {
        return certificadoRepository.findById(id);
    }

    public Certificate salvar(Certificate certificado) {
        return certificadoRepository.save(certificado);
    }

    public void deletar(Long id) {
        certificadoRepository.deleteById(id);
    }
}
