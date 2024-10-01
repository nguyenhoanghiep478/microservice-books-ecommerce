package com.booksms.store.web.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenericMapper<Entity, Response, Request> {
    private final ModelMapper modelMapper;

    public Entity toEntity(Request request, Class<Entity> entityClass) {
        return this.modelMapper.map(request, entityClass);
    }

    public Response toResponse(Entity entity, Class<Response> responseClass) {
        return this.modelMapper.map(entity, responseClass);
    }

    public List toListEntity(List<Request> requests, Class<Entity> entityClass) {
        return (List)requests.stream().map((request) -> {
            return this.toEntity(request, entityClass);
        }).collect(Collectors.toList());
    }

    public List toListResponse(List<Entity> entities, Class<Response> responseClass) {
        return (List)entities.stream().map((entity) -> {
            return this.toResponse(entity, responseClass);
        }).collect(Collectors.toList());
    }

}