package me.elordenador.proyectobackend.service;

import me.elordenador.proyectobackend.models.Image;
import me.elordenador.proyectobackend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service

public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    private final Path root;

    public ImageService(@Value("${storage.location:uploads}") String storageLocation) {
        this.root = Paths.get(storageLocation);
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar la carpeta de almacenamiento", e);
        }
    }

    public Image upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("No se puede guardar un archivo vacio.");

        }

        try {
            String originalName = StringUtils.cleanPath(file.getOriginalFilename());
            String extension = originalName.substring(originalName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + extension;

            Path destinationFile = this.root.resolve(Paths.get(uniqueFileName)).normalize().toAbsolutePath();

            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            Image image = new Image();
            image.setFileName(uniqueFileName);
            image.setOriginalName(originalName);
            image.setMimeType(file.getContentType());

            return imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException("Error al almacenar el archivo: " + e.getMessage());
        }
    }

    public void deleteImage(Image image) {
        try {
            imageRepository.delete(image);

            Path fileToDelete = this.root.resolve(image.getFileName());
            Files.deleteIfExists(fileToDelete);
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar el archivo físico", e);
        }
    }
}
