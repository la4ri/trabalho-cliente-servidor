package org.example.Service;

import org.example.Model.Category;
import org.example.Model.Course;
import org.example.Model.DTO.CourseDTO;
import org.example.Model.Lesson;
import org.example.Repository.CategoryRepository;
import org.example.Repository.CourseRepository;
import org.example.Repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository cursoRepository;

    @Autowired
    private LessonRepository aulaRepository;

    @Autowired
    private CategoryRepository categoriaRepository;

    public List<CourseDTO> listarTodos() {
        return cursoRepository.findAll().stream().map(this::toCourseDTO).collect(Collectors.toList());
    }

    public Set<Course> findAllByIds(Set<Long> ids) {
        // Verifica se os IDs são nulos ou vazios
        if (ids == null || ids.isEmpty()) {
            return new HashSet<>(); // Retorna um conjunto vazio
        }
        // Busca os cursos pelos IDs fornecidos
        return new HashSet<>(cursoRepository.findAllById(ids));
    }

    public Optional<CourseDTO> buscarPorId(Long id) {
        return cursoRepository.findById(id).map(this::toCourseDTO);
    }

    public Course salvar(CourseDTO courseDTO) {
        // Busca a categoria pelo ID fornecido
        Category categoria = null;
        if (courseDTO.getCategoriaId() != null) {
            categoria = categoriaRepository.findById(courseDTO.getCategoriaId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com o ID: " + courseDTO.getCategoriaId()));
        }

        // Cria um novo curso
        Course curso = new Course();
        curso.setNomeCurso(courseDTO.getNomeCurso());
        curso.setDescricao(courseDTO.getDescricao());
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
        if (!cursoRepository.existsById(id)) {
            throw new IllegalArgumentException("Curso não encontrado com o ID: " + id);
        }
        cursoRepository.deleteById(id);
    }

    public CourseDTO toCourseDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setNomeCurso(course.getNomeCurso());
        courseDTO.setDescricao(course.getDescricao());
        courseDTO.setPreco(course.getPreco());

        // Verifica se a categoria não é nula antes de acessar o ID
        if (course.getCategoriaId() != null) {
            courseDTO.setCategoriaId(course.getCategoriaId().getId());
        } else {
            courseDTO.setCategoriaId(null);
        }

        // Inclui apenas os IDs das aulas
        if (course.getAulas() != null) {
            courseDTO.setAulas(course.getAulas().stream().map(Lesson::getId).collect(Collectors.toSet()));
        }

        return courseDTO;
    }
}
