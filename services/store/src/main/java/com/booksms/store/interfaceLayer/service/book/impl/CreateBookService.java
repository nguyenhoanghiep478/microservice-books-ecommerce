package com.booksms.store.interfaceLayer.service.book.impl;

import com.booksms.store.application.usecase.Book.CreateHandlerUseCase.CreateBookHandlerUseCase;
import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.core.domain.entity.Category;
import com.booksms.store.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.store.interfaceLayer.service.book.ICreateBookService;
import com.booksms.store.interfaceLayer.service.category.IFindCategoryService;
import com.booksms.store.web.mapper.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static com.booksms.store.core.domain.constant.STATIC_VAR.IMAGE_STORAGE_PATH;

@Service
@RequiredArgsConstructor
public class CreateBookService implements ICreateBookService {
    private final IFindCategoryService findCategoryService;
    private final GenericMapper<Book, BookRequestDTO, BookRequestDTO> bookMapper;
    private final CreateBookHandlerUseCase bookCreateHandlerUseCase;

    @Override
    @Transactional
    public Book insert(BookRequestDTO request) throws IOException {
        Category category = findCategoryService.findById(request.getCategoryId());

        String pathImage = handleImageToPath(request.getImage(), request.getName());

        Book book = bookMapper.toEntity(request, Book.class);
        book.setImage(pathImage);
        book.setCategory(category);

        return bookCreateHandlerUseCase.execute(book);
    }

    private String handleImageToPath(MultipartFile image,String fileName) throws IOException {
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
