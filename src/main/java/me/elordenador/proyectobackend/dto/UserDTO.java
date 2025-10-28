package me.elordenador.proyectobackend.dto;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.elordenador.proyectobackend.Enums.UserRole;
import me.elordenador.proyectobackend.models.User;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    public UserDTO(User user) {
        setId(user.getId());
        setUsername(user.getUsername());
        setName(user.getName());
        setSurname1(user.getSurname1());
        setSurname2(user.getSurname2());
        setRoles(user.getRoles());
    }
    private Long id;

    private String username;
    private String name;
    private String surname1;
    private String surname2;

    private List<UserRole> roles = new ArrayList<>();
}
