package com.example.footballmanager.dto.mapper.impl;

import com.example.footballmanager.dto.mapper.RequestDtoMapper;
import com.example.footballmanager.dto.mapper.ResponseDtoMapper;
import com.example.footballmanager.dto.request.TeamRequestDto;
import com.example.footballmanager.dto.response.TeamResponseDto;
import com.example.footballmanager.model.Player;
import com.example.footballmanager.model.Team;
import com.example.footballmanager.service.PlayerService;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TeamMapper implements ResponseDtoMapper<TeamResponseDto, Team>,
        RequestDtoMapper<TeamRequestDto, Team> {
    private final PlayerService playerService;

    @Override
    public Team mapToModel(TeamRequestDto dto) {
        Team team = new Team();
        team.setName(dto.getName());
        team.setCountry(dto.getCountry());
        team.setCity(dto.getCity());
        team.setCommission(dto.getCommission());
        team.setBalance(dto.getBalance());
        team.setPlayers(dto.getPlayerIds()
                .stream()
                .map(playerService::get)
                .collect(Collectors.toList()));
        return team;
    }

    @Override
    public TeamResponseDto mapToDto(Team team) {
        TeamResponseDto teamResponseDto = new TeamResponseDto();
        teamResponseDto.setId(team.getId());
        teamResponseDto.setName(team.getName());
        teamResponseDto.setCountry(team.getCountry());
        teamResponseDto.setCity(team.getCity());
        teamResponseDto.setCommission(team.getCommission());
        teamResponseDto.setBalance(team.getBalance());
        teamResponseDto.setPlayerIds(team.getPlayers()
                .stream()
                .map(Player::getId)
                .collect(Collectors.toList()));
        return teamResponseDto;
    }
}
