package com.example.footballmanager.dto.mapper;

import com.example.footballmanager.dto.request.PlayerRequestDto;
import com.example.footballmanager.dto.response.PlayerResponseDto;
import com.example.footballmanager.model.Player;
import com.example.footballmanager.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface PlayerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", source = "teamId", qualifiedByName = "mapTeamIdToTeam")
    Player mapToModel(PlayerRequestDto dto);

    @Mapping(source = "team.id", target = "teamId")
    PlayerResponseDto mapToDto(Player player);

    @Named("mapTeamIdToTeam")
    default Team mapTeamIdToTeam(Long teamId) {
        if (teamId == null) {
            return null;
        }
        Team team = new Team();
        team.setId(teamId);
        return team;
    }
}

