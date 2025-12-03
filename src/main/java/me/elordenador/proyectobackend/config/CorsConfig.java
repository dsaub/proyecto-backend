package me.elordenador.proyectobackend.config;

import lombok.RequiredArgsConstructor;
import me.elordenador.proyectobackend.properties.BackendProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;

@Configuration
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer {
    private final BackendProperty backendProperty;
    @Override
    public void addCorsMappings(CorsRegistry registry) {


        final String frontendOrigin = backendProperty.getUrl();

        registry.addMapping("/**")
                .allowedOrigins(frontendOrigin)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}