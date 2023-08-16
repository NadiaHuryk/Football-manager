package com.example.footballmanager.dto.mapper.impl;

import com.example.footballmanager.dto.mapper.Mapper;
import com.example.footballmanager.dto.request.TeamRequestDto;
import com.example.footballmanager.dto.response.TeamResponseDto;
import com.example.footballmanager.model.Team;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TeamMapper implements Mapper<Team,TeamRequestDto, TeamResponseDto> {

    @Override
    public Team mapToModel(TeamRequestDto dto) {
        Team team = new Team();
        team.setName(dto.getName());
        team.setCountry(dto.getCountry());
        team.setCity(dto.getCity());
        team.setCommission(dto.getCommission());
        team.setBalance(dto.getBalance());
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
        return teamResponseDto;
    }
}
