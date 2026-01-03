package me.elordenador.proyectobackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import me.elordenador.proyectobackend.Enums.UserRole;
import me.elordenador.proyectobackend.dto.UserDTO;
import me.elordenador.proyectobackend.dto.UserPasswordDTO;
import me.elordenador.proyectobackend.exceptions.LoginInvalid;
import me.elordenador.proyectobackend.models.User;
import me.elordenador.proyectobackend.service.UserService;
import me.elordenador.proyectobackend.utils.JwtUtil;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User-Controller
 * @author Daniel Sánchez Úbeda
 * @version 1.0
 */
@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {


    class StringJson {
        public String respuesta;
    }
    private final UserService userService;

    /**
     * Login method
     * @param dto User-Password information.
     * @return 200 with the token as a String if authentication details are valid, 404 if not.
     */
    @PostMapping("/login")
    public ResponseEntity<StringJson> getToken(@RequestBody UserPasswordDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        System.out.println("Logging in as " + username + "...");
        String token;
        try {
            token = userService.login(username, password);
        } catch (LoginInvalid e) {
            return ResponseEntity.notFound().build();
        }
        StringJson json = new StringJson();
        json.respuesta = token;

        return ResponseEntity.ok(json);
    }

    /**
     * Verify Authorization header format function
     * @param token Authorization header (Bearer (token))
     * @return null if invalid or the token itself if valid
     */
    public static String verifyAuthFormat(String token) {
        if (token == null) {
            return null;
        }
        String[] splitToken = token.split(" ");
        if (!splitToken[0].equals("Bearer")) {
            return null;
        }
        return splitToken[1];
    }

    /**
     * Show information of actual user
     * @param token Authorization header for user, must be a valid JWT token for this to work.
     * @return User object.
     */
    @Operation(summary = "Obtiene la información del usuario a partir de su token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El token es valido"),
            @ApiResponse(responseCode = "401", description = "El token no se ha enviado o es invalido", content = @Content)
    })
    @GetMapping("/me")
    public ResponseEntity<UserDTO> aboutMe(@RequestHeader("Authorization") String token) {


        token = verifyAuthFormat(token);
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (JwtUtil.validateToken(token)) {
            String username = JwtUtil.getSubjectFromToken(token);
            User user = userService.getByUsername(username);
            UserDTO dto = new UserDTO(user);
            return ResponseEntity.ok(dto);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Get all users, you need to provide a Authorization token of a ADMIN user for this to work
     * @param token Bearer (token) -- Authorization header.
     * @return 406 if token format is invalid or not provided, 401 if token invalid, 403 if token is valid but user is not admin and 200 with all users when admin valid token is provided
     */
    @Operation
    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAll(@RequestHeader("Authorization") String token) {
        token = verifyAuthFormat(token);
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        if (JwtUtil.validateToken(token)) {
            String username = JwtUtil.getSubjectFromToken(token);
            User user = userService.getByUsername(username);
            List<UserRole> roles = user.getRoles();
            boolean isAdmin = false;
            for (UserRole role : roles) {
                if (role == UserRole.ADMIN) {
                    isAdmin = true;
                }
            }

            if (!isAdmin) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                return ResponseEntity.ok(userService.getAll());
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Get a user by its ID. Requires ADMIN user privileges
     * @param token Authorization header (Bearer token)
     * @param id User ID
     * @return JSON data of the user.
     */
    @GetMapping("/by-id/{id}")
    public ResponseEntity<UserDTO> getById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        token = verifyAuthFormat(token);
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        if (JwtUtil.validateToken(token)) {
            String username = JwtUtil.getSubjectFromToken(token);
            User user = userService.getByUsername(username);
            List<UserRole> roles = user.getRoles();
            boolean isAdmin = false;
            for (UserRole role : roles) {
                if (role == UserRole.ADMIN) {
                    isAdmin = true;
                }
            }

            if (!isAdmin) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            return ResponseEntity.ok(new UserDTO(userService.getById(id)));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
