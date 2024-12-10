package org.example.Service;

import org.example.Model.Category;
import org.example.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoriaRepository;

    public List<Category> listarTodos() {
        return categoriaRepository.findAll();
    }

    public Optional<Category> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Category salvar(Category certificado) {
        return categoriaRepository.save(certificado);
    }

    public void deletar(Long id) {
        categoriaRepository.deleteById(id);
    }
}