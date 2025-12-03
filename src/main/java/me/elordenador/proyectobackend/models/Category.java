package me.elordenador.proyectobackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Category {
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Category parent;
}