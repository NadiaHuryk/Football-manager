package com.example.footballmanager.dto.mapper;

import com.example.footballmanager.dto.request.TeamRequestDto;
import com.example.footballmanager.dto.response.TeamResponseDto;
import com.example.footballmanager.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface TeamMapper {
    @Mapping(target = "id", ignore = true)
    Team mapToModel(TeamRequestDto dto);

    TeamResponseDto mapToDto(Team team);
}
