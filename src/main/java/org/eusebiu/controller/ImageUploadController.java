package org.eusebiu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin
public class ImageUploadController {

    // Folderul local unde salvam pozele
    private final String UPLOAD_DIR = "uploads/";

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Ne asiguram ca exista folderul "uploads"
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Curatam numele fisierului si adaugam un UUID pentru a evita numele duplicate
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

            // Copiem fisierul pe disk
            Path targetLocation = Paths.get(UPLOAD_DIR).resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Returnam URL-ul public catre fisier (acesta va fi preluat si de frontend)
            String fileDownloadUri = "http://localhost:8080/uploads/" + uniqueFileName;

            return ResponseEntity.ok(Map.of("imageUrl", fileDownloadUri));
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Nu s-a putut incarca poza pe server!"));
        }
    }
}
