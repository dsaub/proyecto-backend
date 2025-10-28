package me.elordenador.proyectobackend.service;

import lombok.RequiredArgsConstructor;
import me.elordenador.proyectobackend.dto.UserDTO;
import me.elordenador.proyectobackend.exceptions.LoginInvalid;
import me.elordenador.proyectobackend.exceptions.TokenInvalidException;
import me.elordenador.proyectobackend.libs.StringGenerator;
import me.elordenador.proyectobackend.models.Token;
import me.elordenador.proyectobackend.models.User;
import me.elordenador.proyectobackend.repository.TokenRepository;
import me.elordenador.proyectobackend.repository.UserRepository;
import me.elordenador.proyectobackend.utils.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * User Service
 * @author Daniel Sánchez Úbeda
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    /**
     * Gets a user by its username
     * @param username The user's username
     * @return The user object or null if not found.
     */
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Get a token using username and password
     * @param username User's username
     * @param password User's password (without hashing)
     * @return Token
     * @throws LoginInvalid if user/password is invalid
     */
    public String login(String username, String password) throws LoginInvalid {
        User user = getByUsername(username);
        if (user == null) {
            throw new LoginInvalid("User/Password incorrect");
        }
        if (passwordEncoder.matches(password, user.getPassword())) {
            return JwtUtil.generateToken(user.getUsername());
        } else {
            throw new LoginInvalid("User/Password incorrect");
        }
    }

    public List<UserDTO> getAll() {
        ArrayList<UserDTO> dtos = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            dtos.add(new UserDTO(user));
        }
        return dtos.stream().toList();

    }

    public User getById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            return null;
        }
    }
}
