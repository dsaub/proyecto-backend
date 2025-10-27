package me.elordenador.proyectobackend.repository;

import me.elordenador.proyectobackend.models.Token;
import me.elordenador.proyectobackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, User> {
    Token save(Token token);
    List<Token> findByUser(User user);
    Token getByToken(String token);

}
