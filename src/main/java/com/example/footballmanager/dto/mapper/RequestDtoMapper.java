package com.example.footballmanager.dto.mapper;

public interface RequestDtoMapper<D, T> {
    T mapToModel(D dto);
}
