package me.elordenador.proyectobackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.elordenador.proyectobackend.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    Product save(Product product);

    Optional<Product> findById(Long id);
}
