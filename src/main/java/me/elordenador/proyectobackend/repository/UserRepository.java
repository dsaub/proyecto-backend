package me.elordenador.proyectobackend.repository;

import me.elordenador.proyectobackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
    User getById(Long id);

    User findByUsername(String admin);
}
