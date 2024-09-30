package com.booksms.store.interfaceLayer.service.book.impl;

import com.booksms.store.application.model.BookModel;
import com.booksms.store.application.usecase.Book.CreateHandlerUseCase.CreateBookHandlerUseCase;
import com.booksms.store.application.usecase.inventory.FindInventoryUseCase;
import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.store.interfaceLayer.service.book.ICreateBookService;
import com.booksms.store.interfaceLayer.service.category.IFindCategoryService;
import com.booksms.store.interfaceLayer.service.state.image.IImageService;
import com.booksms.store.web.mapper.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final CreateBookHandlerUseCase bookCreateHandlerUseCase;
    private final IImageService imageService;

    @Override
    @Transactional
    public Book insert(BookRequestDTO request) throws IOException {
        String pathImage = imageService.handleImageToPath(request.getImage(), request.getName());
        return bookCreateHandlerUseCase.execute(map(request,pathImage));
    }



    private BookModel map(BookRequestDTO bookRequestDTO,String pathImage) throws IOException {
        return BookModel.builder()
                .name(bookRequestDTO.getName())
                .pathImage(pathImage)
                .price(bookRequestDTO.getPrice())
                .availableQuantity(bookRequestDTO.getAvailableQuantity())
                .title(bookRequestDTO.getTitle())
                .categoryId(bookRequestDTO.getCategoryId())
                .isInStock(bookRequestDTO.getIsInStock())
                .chapter(bookRequestDTO.getChapter())
                .distributorId(bookRequestDTO.getDistributorId())
                .inventoryId(bookRequestDTO.getInventoryId())
                .build();
    }
}
