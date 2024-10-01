package com.booksms.store.application.usecase;

public interface BaseUseCase<Entity,Input>{
    Entity execute(Input input);
}
