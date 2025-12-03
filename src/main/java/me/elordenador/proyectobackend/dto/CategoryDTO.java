package me.elordenador.proyectobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.elordenador.proyectobackend.models.Category;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private ArrayList<CategoryDTO> childs = new ArrayList<>();


    public void add(CategoryDTO dto) {
        childs.add(dto);
    }

    public CategoryDTO(Category category) {
        setName(category.getName());
        setId(category.getId());
        // TODO: Finish this
    }
}
