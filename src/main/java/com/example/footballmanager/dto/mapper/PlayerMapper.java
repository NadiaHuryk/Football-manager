package com.example.footballmanager.dto.mapper;

import com.example.footballmanager.dto.request.PlayerRequestDto;
import com.example.footballmanager.dto.response.PlayerResponseDto;
import com.example.footballmanager.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface PlayerMapper {
    @Mapping(target = "team.id", source = "teamId")
    Player mapToModel(PlayerRequestDto dto);

    @Mapping(target = "teamId", source = "team.id")
    PlayerResponseDto mapToDto(Player player);
}

