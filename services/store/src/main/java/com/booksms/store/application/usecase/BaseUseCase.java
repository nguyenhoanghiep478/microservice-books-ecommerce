package com.booksms.store.application.usecase;

import java.io.IOException;

public interface BaseUseCase<Entity,Param>{
    Entity execute( Param param) throws IOException;
}
