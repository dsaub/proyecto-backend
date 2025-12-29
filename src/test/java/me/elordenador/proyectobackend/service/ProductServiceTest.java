package me.elordenador.proyectobackend.service;

import jakarta.transaction.Transactional;
import me.elordenador.proyectobackend.models.Category;
import me.elordenador.proyectobackend.models.Product;
import me.elordenador.proyectobackend.repository.CategoryRepository;
import me.elordenador.proyectobackend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProductServiceTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    private Category category1;
    private Category category2;

    private Product product1, product2;
    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        category1 = Category.builder().name("Ordenadores").parent(null).build();
        category2 = Category.builder().name("Portatiles").parent(category1).build();
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        product1 = Product.builder().name("Acer Aspire X3990").price(99.99F).category(category1).build();
        productService.store(product1);
        product2 = Product.builder().name("HP 250 G3").price(99.99F).category(category2).build();
        productService.store(product2);


    }

    @Test
    void testGetById() {
        Product result = productService.getById(product1.getId());
        assertNotNull(result);
        assertEquals("Acer Aspire X3990", result.getName());
        assertEquals(product1.getId(), result.getId());

    }
}
