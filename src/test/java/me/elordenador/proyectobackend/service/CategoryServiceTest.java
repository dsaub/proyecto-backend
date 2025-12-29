package me.elordenador.proyectobackend.service;

import me.elordenador.proyectobackend.dto.CategoryDTO;
import me.elordenador.proyectobackend.models.Category;
import me.elordenador.proyectobackend.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CategoryServiceTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    private Category parentCategory;
    private Category childCategory;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();

        parentCategory = Category.builder().name("Electrónica").parent(null).build();
        parentCategory = categoryRepository.save(parentCategory);

        childCategory = Category.builder().name("Ordenadores").parent(parentCategory).build();
        childCategory = categoryRepository.save(childCategory);
    }

    @Test
    void testGetByIdFound() {
        Category result = categoryService.getById(parentCategory.getId());

        assertNotNull(result);
        assertEquals("Electrónica", result.getName());
        assertEquals(parentCategory.getId(), result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        Category result = categoryService.getById(999L);

        assertNull(result);
    }

    @Test
    void testGetParentCategory() {
        Category result = categoryService.getParentCategory(childCategory);

        assertNotNull(result);
        assertEquals("Electrónica", result.getName());
        assertEquals(parentCategory.getId(), result.getId());
    }

    @Test
    void testGetParentCategoryNotFound() {
        Category result = categoryService.getParentCategory(parentCategory);

        assertNull(result);
    }

    @Test
    void testGetAllWithHierarchy() {
        List<CategoryDTO> result = categoryService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Electrónica", result.getFirst().getName());
        assertEquals(1, result.getFirst().getChilds().size());
        assertEquals("Ordenadores", result.getFirst().getChilds().getFirst().getName());
    }

    @Test
    void testGetAllEmpty() {
        categoryRepository.deleteAll();

        List<CategoryDTO> result = categoryService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
