package me.elordenador.proyectobackend.repository;

import me.elordenador.proyectobackend.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image save(Image image);
}
