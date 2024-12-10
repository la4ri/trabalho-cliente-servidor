package org.example.Controller;


import org.example.Model.Category;
import org.example.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
public class CategoryController {

    @Autowired
    private CategoryService categoriaService;

    @GetMapping
    public List<Category> listarTodos() {
        return categoriaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Category criar(@RequestBody Category categoria) {
        return categoriaService.salvar(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> atualizar(@PathVariable Long id, @RequestBody Category categoriaAtualizado) {
        return categoriaService.buscarPorId(id)
                .map(categoriaExistente -> {
                    categoriaExistente.setNomeCategoria(categoriaAtualizado.getNomeCategoria());
                    return ResponseEntity.ok(categoriaService.salvar(categoriaExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (categoriaService.buscarPorId(id).isPresent()) {
            categoriaService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}