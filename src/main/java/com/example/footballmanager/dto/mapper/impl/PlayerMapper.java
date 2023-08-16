package com.example.footballmanager.dto.mapper.impl;

import com.example.footballmanager.dto.mapper.Mapper;
import com.example.footballmanager.dto.request.PlayerRequestDto;
import com.example.footballmanager.dto.response.PlayerResponseDto;
import com.example.footballmanager.model.Player;
import com.example.footballmanager.model.Team;
import com.example.footballmanager.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlayerMapper implements Mapper<Player, PlayerRequestDto, PlayerResponseDto> {
    private TeamService teamService;

    @Override
    public Player mapToModel(PlayerRequestDto dto) {
        Player player = new Player();
        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        player.setBirthDate(dto.getBirthDate());
        player.setCareerStartDate(dto.getCareerStartDate());
        player.setTeam(teamService.get(dto.getTeamId()));
        if (dto.getTeamId() != null) {
            player.setTeam(teamService.get(dto.getTeamId()));
        }
        return player;
    }

    @Override
    public PlayerResponseDto mapToDto(Player player) {
        PlayerResponseDto playerResponseDto = new PlayerResponseDto();
        playerResponseDto.setId(player.getId());
        playerResponseDto.setFirstName(player.getFirstName());
        playerResponseDto.setLastName(player.getLastName());
        playerResponseDto.setBirthDate(player.getBirthDate());
        playerResponseDto.setCareerStartDate(player.getCareerStartDate());

        Team team = player.getTeam();
        if (team != null) {
            playerResponseDto.setTeamId(team.getId());
        }

        return playerResponseDto;
    }
}
