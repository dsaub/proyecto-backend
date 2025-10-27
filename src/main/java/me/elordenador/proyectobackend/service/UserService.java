package me.elordenador.proyectobackend.service;

import lombok.RequiredArgsConstructor;
import me.elordenador.proyectobackend.exceptions.LoginInvalid;
import me.elordenador.proyectobackend.exceptions.TokenInvalidException;
import me.elordenador.proyectobackend.libs.StringGenerator;
import me.elordenador.proyectobackend.models.Token;
import me.elordenador.proyectobackend.models.User;
import me.elordenador.proyectobackend.repository.TokenRepository;
import me.elordenador.proyectobackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String login(String username, String password) {
        User user = getByUsername(username);
        if (user == null) {
            throw new LoginInvalid("User/Password incorrect");
        }

        if (user.getPassword().equals(passwordEncoder.encode(password))) {
            String token1 = StringGenerator.generate(20);
            Token token = new Token(token1, user);
            tokenRepository.save(token);
            return token1;
        } else {
            throw new LoginInvalid("User/Password incorrect");
        }
    }

    public User auth(String token) {
        if (token.length() != 20) {
            throw new TokenInvalidException("Token must be 20 letters long");
        }

        Token token1 = tokenRepository.getByToken(token);
        if (token1 == null) {
            throw new TokenInvalidException("Token is invalid");
        }

        return token1.getUser();
    }
}
