package org.example.Controller;

import org.example.Model.Course;
import org.example.Model.User;
import org.example.Service.CourseService;
import org.example.Service.UserService;
import org.example.Model.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserService usuarioService;

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> listarTodos() {
        List<UserDTO> usuarios = usuarioService.listarTodos()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDTO> criar(@RequestBody UserDTO userDTO) {
        // Converte DTO para entidade
        Set<Course> cursos = courseService.findAllByIds(userDTO.getCursoIds());
        User user = convertToEntity(userDTO, cursos);

        // Salva o usuário
        User savedUser = usuarioService.salvar(user);

        // Converte entidade para DTO
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> atualizar(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return usuarioService.buscarPorId(id)
                .map(usuarioExistente -> {
                    // Atualiza os campos do usuário
                    usuarioExistente.setNome(userDTO.getNome());
                    usuarioExistente.setEmail(userDTO.getEmail());
                    usuarioExistente.setOcupacao(userDTO.getOcupacao());

                    // Atualiza os cursos
                    Set<Course> cursos = courseService.findAllByIds(userDTO.getCursoIds());
                    usuarioExistente.setCursos(cursos);

                    // Salva e retorna o DTO atualizado
                    User updatedUser = usuarioService.salvar(usuarioExistente);
                    return ResponseEntity.ok(convertToDTO(updatedUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (usuarioService.buscarPorId(id).isPresent()) {
            usuarioService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Métodos auxiliares para conversão

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNome(user.getNome());
        dto.setEmail(user.getEmail());
        dto.setOcupacao(user.getOcupacao());
        dto.setCursoIds(
                user.getCursos().stream().map(Course::getId).collect(Collectors.toSet())
        );
        return dto;
    }

    private User convertToEntity(UserDTO dto, Set<Course> cursos) {
        User user = new User();
        user.setId(dto.getId());
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setOcupacao(dto.getOcupacao());
        user.setCursos(cursos);
        return user;
    }
}
