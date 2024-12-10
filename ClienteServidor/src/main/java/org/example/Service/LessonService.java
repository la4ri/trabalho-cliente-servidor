package org.example.Service;

import org.example.Model.Lesson;
import org.example.Repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    @Autowired
    private LessonRepository aulasRepository;

    public List<Lesson> listarTodos() {
        return aulasRepository.findAll();
    }

    public Optional<Lesson> buscarPorId(Long id) {
        return aulasRepository.findById(id);
    }

    public Lesson salvar(Lesson certificado) {
        return aulasRepository.save(certificado);
    }

    public void deletar(Long id) {
        aulasRepository.deleteById(id);
    }
}