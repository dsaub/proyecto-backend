package me.elordenador.proyectobackend.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.elordenador.proyectobackend.models.Product;
import me.elordenador.proyectobackend.repository.ProductRepository;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }
    public Product getByName(String name) {
        return productRepository.findByName(name).orElseThrow();
    }
    public void store(Product product) {
        productRepository.save(product);
    }
}
