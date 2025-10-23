package me.elordenador.proyectobackend.repository;

import me.elordenador.proyectobackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Long, User> {
    User save(User user);
    Optional<User> getById(Long id);

    Collection<User> findByUsername(String admin);
}
