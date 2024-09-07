package com.booksms.store.application.usecase.Book.UpdateUseCase;

import com.booksms.store.application.model.BookModel;
import com.booksms.store.application.model.CategorySearchCriteria;
import com.booksms.store.application.usecase.BaseUseCase;
import com.booksms.store.application.usecase.Category.FindCategoryUseCase;
import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.core.domain.entity.Category;
import com.booksms.store.core.domain.exception.BookExpcetion.BookNotFoundException;
import com.booksms.store.core.domain.exception.InSufficientQuantityException;
import com.booksms.store.core.domain.exception.UpdateFailureException;
import com.booksms.store.core.domain.repository.IBookRepository;
import com.booksms.store.interfaceLayer.DTO.Request.OrderType;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static com.booksms.store.core.domain.constant.STATIC_VAR.IMAGE_STORAGE_PATH;

@Component
@RequiredArgsConstructor
public class UpdateBookUseCase implements BaseUseCase<Book, BookModel> {
    private final IBookRepository bookRepository;
    private final FindCategoryUseCase findCategoryUseCase;
    @Override
    @Transactional(rollbackFor = BookNotFoundException.class)
    public Book execute(@NotNull BookModel request) throws IOException {
        //check book
        Book oldBook = bookRepository.findOneByBookId(request.getId()).orElseThrow(
                () -> new BookNotFoundException(String.format("Book with name '%s' not found", request.getName()))
        );

        return bookRepository.save(mergeEntity(oldBook, request)).orElseThrow(
                () -> new UpdateFailureException(String.format("Book with name '%s' update failed", request.getName()))
        );
    }


    private Book mergeEntity(Book oldBook,BookModel newBook) throws IOException {
            if(newBook.getPrice() != null){
                oldBook.setPrice(newBook.getPrice());
            }
            if(newBook.getName() != null){
                oldBook.setName(newBook.getName());
            }
            if(newBook.getTitle() != null){
                oldBook.setTitle(newBook.getTitle());
            }
            if(newBook.getCategoryId() != null){
               Category category = findCategoryUseCase.execute(CategorySearchCriteria.builder()
                                .id(newBook.getCategoryId())
                        .build());
                oldBook.setCategory(category);
            }
            if(newBook.getDistributorId() != null){
                oldBook.setDistributorId(newBook.getDistributorId());
            }
            if(newBook.getImage() != null){
               String lastFileName = handleImageToPath(newBook.getImage(), oldBook.getName());
                oldBook.setImage(lastFileName);
            }
            if(newBook.getName() != null){
                oldBook.setName(newBook.getName());
            }
            if(newBook.getAvailableQuantity() != null){
                if(newBook.getOrderType() == OrderType.SELL){
                    if(oldBook.getAvailableQuantity() < newBook.getAvailableQuantity()) {
                        throw new InSufficientQuantityException("not enough available quantity");
                    }
                    oldBook.setAvailableQuantity(oldBook.getAvailableQuantity() - newBook.getAvailableQuantity());
                }else if(newBook.getOrderType() == OrderType.BUY){
                    oldBook.setAvailableQuantity(oldBook.getAvailableQuantity() + newBook.getAvailableQuantity());
                }else{
                    oldBook.setAvailableQuantity(newBook.getAvailableQuantity());
                }
            }
            if(newBook.getIsInStock() != null){
                oldBook.setIsInStock(newBook.getIsInStock());
            }
        return oldBook;
    }

    private String handleImageToPath(MultipartFile image, String fileName) throws IOException {
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
