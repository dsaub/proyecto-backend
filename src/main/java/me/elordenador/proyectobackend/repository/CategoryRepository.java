package me.elordenador.proyectobackend.repository;

import me.elordenador.proyectobackend.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category save(Category category);


    Optional<Category> findById(Long id);
    Optional<Category> findByName(String name);
    Optional<Category> findCategoryByParent(Category parent);
}
