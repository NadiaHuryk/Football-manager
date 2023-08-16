package com.example.footballmanager.dto.mapper;

public interface Mapper<D, T, S> {
    D mapToModel(T dto);

    S mapToDto(D t);
}
