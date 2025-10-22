package me.elordenador.proyectobackend.repository;

import me.elordenador.proyectobackend.models.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> getById(Long id);

    Collection<User> findByUsername(String admin);
}
