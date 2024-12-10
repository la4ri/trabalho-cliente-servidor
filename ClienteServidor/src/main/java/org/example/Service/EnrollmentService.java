package org.example.Service;

import org.example.Model.Course;
import org.example.Model.Enrollment;
import org.example.Model.User;
import org.example.Repository.CourseRepository;
import org.example.Repository.EnrollmentRepository;
import org.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository matriculaRepository;

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private CourseRepository cursoRepository;

    public List<Enrollment> listarTodos() {
        return matriculaRepository.findAll();
    }

    public Optional<Enrollment> buscarPorId(Long id) {
        return matriculaRepository.findById(id);
    }

    public Enrollment salvar(Long usuario_id, Long curso_id) {
        // Busca o usuário pelo ID
        User usuario = usuarioRepository.findById(usuario_id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + usuario_id));

        // Busca o curso pelo ID
        Course curso = cursoRepository.findById(curso_id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado com o ID: " + curso_id));

        // Cria a matrícula
        Enrollment matricula = new Enrollment();
        matricula.setUsuario(usuario);
        matricula.setCurso(curso);

        // Salva a matrícula
        return matriculaRepository.save(matricula);
    }

    public void deletar(Long id) {
        matriculaRepository.deleteById(id);
    }
}
