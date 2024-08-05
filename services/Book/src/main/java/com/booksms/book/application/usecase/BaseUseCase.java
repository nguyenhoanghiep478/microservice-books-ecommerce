package com.booksms.book.application.usecase;

public interface BaseUseCase<Entity,Param>{
    Entity execute( Param param);
}
