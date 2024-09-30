package com.booksms.store.interfaceLayer.service.state.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImageService {
    String getImageBase64(String path) throws IOException;
    String handleImageToPath(MultipartFile image, String fileName) throws IOException;
}
