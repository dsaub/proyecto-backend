package me.elordenador.proyectobackend.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.elordenador.proyectobackend.Enums.UserRole;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id private Long id;
    private String username;
    private String password;
    private String name, surname1, surname2;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<UserRole> roles = new ArrayList<>();
}
