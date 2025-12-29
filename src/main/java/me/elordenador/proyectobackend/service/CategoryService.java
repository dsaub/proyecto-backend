package me.elordenador.proyectobackend.service;


import lombok.RequiredArgsConstructor;
import me.elordenador.proyectobackend.dto.CategoryDTO;
import me.elordenador.proyectobackend.exceptions.NotFoundException;
import me.elordenador.proyectobackend.models.Category;
import me.elordenador.proyectobackend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService{
    private final CategoryRepository categoryRepository;
    public Category getById(Long id) throws NotFoundException {
        Optional<Category> oCategory = categoryRepository.findById(id);
        return oCategory.orElse(null);
    }

    public Category getParentCategory(Category category) {
        return category.getParent();
    }
    public List<CategoryDTO> getAll() {
        ArrayList<CategoryDTO> dtos = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            if (category.getParent() == null) {
                dtos.add(new CategoryDTO(category));
            }

        }

        if (dtos.size() != 0) {
            for (CategoryDTO category : dtos) {
                getTree(category);

            }
        }
        return dtos.stream().toList();
    }
    public ArrayList<CategoryDTO> getTree(CategoryDTO categoryDTO) {

        List<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            if (category.getParent() != null) {
                if (Objects.equals(category.getParent().getId(), categoryDTO.getId())) {
                    categoryDTO.add(new CategoryDTO(category));
                }
            }

        }
        ArrayList<CategoryDTO> dtos = categoryDTO.getChilds();
        if (dtos.size() != 0) {
            for (CategoryDTO category : dtos) {
                getTree(category);
            }
        }
        return dtos;

    }

}
