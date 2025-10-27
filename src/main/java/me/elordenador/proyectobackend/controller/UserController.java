package me.elordenador.proyectobackend.controller;

import lombok.RequiredArgsConstructor;
import me.elordenador.proyectobackend.dto.UserDTO;
import me.elordenador.proyectobackend.exceptions.LoginInvalid;
import me.elordenador.proyectobackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/public/departamento")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<String> getToken(@RequestBody UserDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        String token;
        try {
            token = userService.login(username, password);
        } catch (LoginInvalid e) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok(token);
    }
}
