package me.elordenador.proyectobackend;

import me.elordenador.proyectobackend.Enums.UserRole;
import me.elordenador.proyectobackend.models.User;
import me.elordenador.proyectobackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class ProyectoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner initUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            System.out.println("Creando usuario...");
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .name("Administrador")
                        .surname1("del")
                        .surname2("Sistema")
                        .roles(List.of(UserRole.ADMIN, UserRole.BUYER))
                        .build();
                userRepository.save(admin);
                System.out.println("Usuario ADMIN creado");
            }
        };
    }

}
