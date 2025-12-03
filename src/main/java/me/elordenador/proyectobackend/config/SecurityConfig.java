package me.elordenador.proyectobackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    // **NOTA IMPORTANTE:** Al usar .authorizeHttpRequests().anyRequest().permitAll()
    // y .csrf().disable() se desactiva de facto la seguridad de acceso a recursos.
    // Esto es lo que necesitas para tu caso de uso.

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Deshabilitar CSRF (Crucial para APIs REST)
                .csrf(csrf -> csrf.disable())

                // 2. Habilitar CORS. Esto permite que la configuración de tu clase CorsConfig
                // (WebMvcConfigurer) sea aplicada correctamente.
                .cors(Customizer.withDefaults())

                // 3. DESACTIVAR AUTORIZACIÓN: Permite acceso a CUALQUIER solicitud.
                // Esto cumple tu requisito de NO proteger los endpoints.
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                );

        // Puedes agregar más configuraciones si es necesario, pero estas son las mínimas
        // para desactivar la protección y habilitar CORS.

        return http.build();
    }

    // --- Componente de Password Hashing ---

    @Bean
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        // Expones el Bean para que puedas inyectarlo en tus servicios para hashear
        return new BCryptPasswordEncoder();
    }
}