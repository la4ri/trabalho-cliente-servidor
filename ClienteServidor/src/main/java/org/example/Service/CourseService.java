package org.example.Service;

import org.example.Model.Course;
import org.example.Model.Lesson;
import org.example.Repository.CourseRepository;
import org.example.Repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseService {

    @Autowired
    private CourseRepository cursoRepository;

    @Autowired
    private LessonRepository aulaRepository;

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

    public Course salvar(Course curso) {
        return cursoRepository.save(curso);
    }

    public void deletar(Long id) {
        cursoRepository.deleteById(id);
    }
}
