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
import static org.junit.jupiter.api.Assertions.assertNull;

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
        Product result1 = productService.getById(product1.getId());
        assertNotNull(result1);
        assertEquals(product1.getName(), result1.getName());
        assertEquals(product1.getId(), result1.getId());
        assertEquals(product1.getPrice(), result1.getPrice());
        Product result2 = productService.getById(product2.getId());
        assertNotNull(result2);
        assertEquals(product2.getName(), result2.getName());
        assertEquals(product2.getId(), product2.getId());
        assertEquals(product2.getPrice(), result2.getPrice());
    }

    @Test
    void testGetByName() {
        Product result1 = productService.getByName(product1.getName());
        Product result2 = productService.getByName(product2.getName());

        assertNotNull(result1);
        assertNotNull(result2);

        assertEquals(product1.getId(), result1.getId());
        assertEquals(product1.getName(), result1.getName());
        assertEquals(product1.getPrice(), result1.getPrice());

        assertEquals(product2.getId(), result2.getId());
        assertEquals(product2.getName(), result2.getName());
        assertEquals(product2.getPrice(), result2.getPrice());
    }

    @Test
    void testGetNonExistingProducct() {
        Product result = productService.getById(111111L);
        assertNull(result);
    }
}
