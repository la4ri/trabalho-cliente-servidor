package org.example.Service;

import org.example.Model.Category;
import org.example.Model.Course;
import org.example.Model.DTO.CourseDTO;
import org.example.Model.Lesson;
import org.example.Model.User;
import org.example.Repository.CategoryRepository;
import org.example.Repository.CourseRepository;
import org.example.Repository.LessonRepository;
import org.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseService {

    @Autowired
    private CourseRepository cursoRepository;

    @Autowired
    private LessonRepository aulaRepository;

    @Autowired
    private CategoryRepository categoriaRepository;

    @Autowired
    private UserRepository usuarioRepository;

    public Course adicionarAula(Long courseId, Set<Long> lessonIds) {
        Optional<Course> optionalCourse = cursoRepository.findById(courseId);
        if (optionalCourse.isEmpty()) {
            throw new RuntimeException("Curso não encontrado");
        }

        Course course = optionalCourse.get();
        List<Lesson> aula = aulaRepository.findAllById(lessonIds);
        course.getAulas().addAll(aula);
        return cursoRepository.save(course);
    }

        public List<Course> listarTodos() {
        return cursoRepository.findAll();
    }

    public Optional<Course> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Course salvar(CourseDTO courseDTO) {
        // Busca o professor e a categoria pelos IDs fornecidos
        User usuario = usuarioRepository.findById(courseDTO.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado com o ID: " + courseDTO.getUsuarioId()));
        Category categoria = categoriaRepository.findById(courseDTO.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com o ID: " + courseDTO.getCategoriaId()));

        // Cria um novo curso
        Course curso = new Course();
        curso.setNomeCurso(courseDTO.getNomeCurso());
        curso.setDescricao(courseDTO.getDescricao());
        curso.setUsuarioId(usuario);
        curso.setCategoriaId(categoria);
        curso.setPreco(courseDTO.getPreco());

        // Adiciona as aulas, se fornecidas
        if (courseDTO.getAulas() != null && !courseDTO.getAulas().isEmpty()) {
            curso.setAulas(new HashSet<>(aulaRepository.findAllById(courseDTO.getAulas())));
        }

        return cursoRepository.save(curso);
    }

    public Course atualizar(Long id, CourseDTO courseDTO) {
        // Busca o curso existente
        Course cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado com o ID: " + id));

        // Atualiza os campos
        if (courseDTO.getNomeCurso() != null) {
            cursoExistente.setNomeCurso(courseDTO.getNomeCurso());
        }
        if (courseDTO.getDescricao() != null) {
            cursoExistente.setDescricao(courseDTO.getDescricao());
        }
        if (courseDTO.getUsuarioId() != null) {
            User usuario = usuarioRepository.findById(courseDTO.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado com o ID: " + courseDTO.getUsuarioId()));
            cursoExistente.setUsuarioId(usuario);
        }
        if (courseDTO.getCategoriaId() != null) {
            Category categoria = categoriaRepository.findById(courseDTO.getCategoriaId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com o ID: " + courseDTO.getCategoriaId()));
            cursoExistente.setCategoriaId(categoria);
        }
        if (courseDTO.getPreco() != null) {
            cursoExistente.setPreco(courseDTO.getPreco());
        }
        if (courseDTO.getAulas() != null && !courseDTO.getAulas().isEmpty()) {
            cursoExistente.setAulas(new HashSet<>(aulaRepository.findAllById(courseDTO.getAulas())));
        }

        return cursoRepository.save(cursoExistente);
    }


    public void deletar(Long id) {
        cursoRepository.deleteById(id);
    }
}
