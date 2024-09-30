package com.booksms.store.application.usecase.state;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static com.booksms.store.core.domain.constant.STATIC_VAR.IMAGE_STORAGE_PATH;

@Component
public class UtilsUseCase {

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
