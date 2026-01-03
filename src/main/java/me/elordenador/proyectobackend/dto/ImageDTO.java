package me.elordenador.proyectobackend.dto;


import lombok.Getter;
import lombok.Setter;
import me.elordenador.proyectobackend.models.Image;

@Getter
@Setter
public class ImageDTO {
    private Long id;

    private String originalName;
    private String fileName;
    private String mimeType;

    private Long user_id;

    public ImageDTO(Image image) {
        setId(image.getId());
        setOriginalName(image.getOriginalName());
        setMimeType(image.getMimeType());
        setUser_id(image.getOwner().getId());
    }
}
