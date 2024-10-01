package com.booksms.authentication.interfaceLayer.service.state.image.impl;

import com.booksms.authentication.interfaceLayer.service.state.image.IImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;

import static com.booksms.authentication.core.constant.STATIC_VAR.IMAGE_STORAGE_PATH;


@Service
public class ImageService implements IImageService {
    @Override
    public String getImageBase64(String path) throws IOException {
        Path filePath = Paths.get(IMAGE_STORAGE_PATH).resolve(path).normalize();
        File file = filePath.toFile();
        if (file.exists()) {
            byte[] fileContent = Files.readAllBytes(filePath);
            return Base64.getEncoder().encodeToString(fileContent);
        }
        return null;
    }

    @Override
    public String handleImageToPath(MultipartFile image, String fileName) throws IOException {
        if (image == null) {
            return null;
        }

        String suffix = Objects.requireNonNull(image.getOriginalFilename()).substring(image.getOriginalFilename().lastIndexOf("."));
        String lastFileName = fileName + suffix;

        String filePath = IMAGE_STORAGE_PATH + lastFileName;

        Files.createDirectories(Paths.get(IMAGE_STORAGE_PATH));

        File destinationFile = new File(filePath);
        image.transferTo(destinationFile);

        return lastFileName;
    }
}
