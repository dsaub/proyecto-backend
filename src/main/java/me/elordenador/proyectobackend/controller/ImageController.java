package me.elordenador.proyectobackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import me.elordenador.proyectobackend.dto.UserDTO;
import me.elordenador.proyectobackend.models.Image;
import me.elordenador.proyectobackend.models.User;
import me.elordenador.proyectobackend.service.ImageService;
import me.elordenador.proyectobackend.service.UserService;
import me.elordenador.proyectobackend.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final UserService userService;

    @PostMapping("/upload")
    @Operation(
            summary = "Sube una imagen"
    )
    @ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Se ha creado la imagen"
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "El token no se ha enviado o no es valido"
                )
        }
    )
    public ResponseEntity<Image> uploadImage(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String token) {
        token = UserController.verifyAuthFormat(token);
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (JwtUtil.validateToken(token)) {
            String username = JwtUtil.getSubjectFromToken(token);
            User user = userService.getByUsername(username);
            Image savedImage = imageService.upload(user, file);
            return new ResponseEntity<>(savedImage, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
}
