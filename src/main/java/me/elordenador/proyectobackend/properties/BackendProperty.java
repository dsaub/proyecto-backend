package me.elordenador.proyectobackend.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("app.api")
public class BackendProperty {
    private String url = "http://localhost";
}
