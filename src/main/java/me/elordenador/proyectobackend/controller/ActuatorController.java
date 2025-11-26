package me.elordenador.proyectobackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * /actuator endpoint
 * @version 0.0.2
 * @author Daniel Sánchez Úbeda
 */
@RestController
@RequestMapping("/actuator")
public class ActuatorController {
    /**
     * Returns pong, used for health check
     * @return pong
     */
    @GetMapping("/health")
    public String getHealth() {
        return "pong";
    }
}
