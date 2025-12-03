package me.elordenador.proyectobackend.controller;


import lombok.RequiredArgsConstructor;
import me.elordenador.proyectobackend.dto.CategoryDTO;
import me.elordenador.proyectobackend.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/tree")
    public List<CategoryDTO> getTree() {
        ArrayList<CategoryDTO> lista = new ArrayList<>();

        return categoryService.getAll();
    }
}
