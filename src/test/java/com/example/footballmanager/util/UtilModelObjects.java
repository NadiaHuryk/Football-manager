package com.example.footballmanager.util;

import com.example.footballmanager.dto.request.PlayerRequestDto;
import com.example.footballmanager.dto.request.TeamRequestDto;
import com.example.footballmanager.dto.response.PlayerResponseDto;
import com.example.footballmanager.dto.response.TeamResponseDto;
import com.example.footballmanager.model.Player;
import com.example.footballmanager.model.Team;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UtilModelObjects {
    protected Player getPlayer() {
        Player createdPlayer = new Player();
        createdPlayer.setId(1L);
        createdPlayer.setFirstName("John");
        createdPlayer.setLastName("Doe");
        createdPlayer.setBirthDate(LocalDate.of(1990, 5, 15));
        createdPlayer.setCareerStartDate(LocalDate.of(2010, 8, 20));
        Team team = new Team();
        team.setId(1L);
        team.setName("Team A");
        team.setCountry("Ukraine");
        team.setCity("Lviv");
        team.setCommission(1);
        team.setBalance(BigDecimal.valueOf(10000));
        createdPlayer.setTeam(team);
        return createdPlayer;
    }

    protected Team getTeam() {
        Team team = new Team();
        team.setId(1L);
        team.setName("Team A");
        team.setCountry("Ukraine");
        team.setCity("Lviv");
        team.setCommission(1);
        team.setBalance(BigDecimal.valueOf(10000));
        return team;
    }

    protected PlayerRequestDto getPlayerRequestDto() {
        PlayerRequestDto playerRequestDto = new PlayerRequestDto();
        playerRequestDto.setFirstName("John");
        playerRequestDto.setLastName("Doe");
        playerRequestDto.setBirthDate(LocalDate.of(1990, 5, 15));
        playerRequestDto.setCareerStartDate(LocalDate.of(2010, 8, 20));
        playerRequestDto.setTeamId(1L);
        return playerRequestDto;
    }

    protected TeamRequestDto getTeamRequestDto() {
        TeamRequestDto teamRequestDto = new TeamRequestDto();
        teamRequestDto.setName("Team A");
        teamRequestDto.setCountry("Ukraine");
        teamRequestDto.setCity("Lviv");
        teamRequestDto.setCommission(1);
        teamRequestDto.setBalance(BigDecimal.valueOf(10000));
        return teamRequestDto;
    }

    protected PlayerResponseDto getPlayerResponseDto() {
        PlayerResponseDto createdPlayerResponseDto = new PlayerResponseDto();
        createdPlayerResponseDto.setId(1L);
        createdPlayerResponseDto.setFirstName("John");
        createdPlayerResponseDto.setLastName("Doe");
        createdPlayerResponseDto.setBirthDate(LocalDate.of(1990, 05, 15));
        createdPlayerResponseDto.setCareerStartDate(LocalDate.of(2010, 8, 20));
        createdPlayerResponseDto.setTeamId(1L);
        return createdPlayerResponseDto;
    }

    protected TeamResponseDto getTeamResponseDto() {
        TeamResponseDto createdTeamResponseDto = new TeamResponseDto();
        createdTeamResponseDto.setId(1L);
        createdTeamResponseDto.setName("Team A");
        createdTeamResponseDto.setCountry("Ukraine");
        createdTeamResponseDto.setCity("Lviv");
        createdTeamResponseDto.setCommission(1);
        createdTeamResponseDto.setBalance(BigDecimal.valueOf(10000));
        return createdTeamResponseDto;
    }

    protected List<Player> getPlayers(int count) {
        List<Player> players = new ArrayList<>();
        for (long i = 1; i <= count; i++) {
            Player player = getPlayer();
            player.setId(i);
            players.add(player);
        }
        return players;
    }

    protected List<Team> getTeams(int count) {
        List<Team> teams = new ArrayList<>();
        for (long i = 1; i <=count; i++) {
            Team team = getTeam();
            team.setId(i);
            teams.add(team);
        }
        return teams;
    }
}
