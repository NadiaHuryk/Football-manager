package com.example.footballmanager.controller;

import com.example.footballmanager.dto.mapper.RequestDtoMapper;
import com.example.footballmanager.dto.mapper.ResponseDtoMapper;
import com.example.footballmanager.dto.request.TeamRequestDto;
import com.example.footballmanager.dto.response.PlayerResponseDto;
import com.example.footballmanager.dto.response.TeamResponseDto;
import com.example.footballmanager.model.Player;
import com.example.footballmanager.model.Team;
import com.example.footballmanager.service.TeamService;
import com.example.footballmanager.service.TransferService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class TeamController {
    private final TeamService teamService;
    private final RequestDtoMapper<TeamRequestDto, Team> requestDtoMapper;
    private final ResponseDtoMapper<TeamResponseDto, Team> responseDtoMapper;
    private final ResponseDtoMapper<PlayerResponseDto, Player> playerDtoMapper;
    private final TransferService transferService;

    @PostMapping
    public TeamResponseDto create(@RequestBody TeamRequestDto requestDto) {
        Team team = teamService.save(requestDtoMapper.mapToModel(requestDto));
        return responseDtoMapper.mapToDto(team);
    }

    @GetMapping("/{id}")
    public TeamResponseDto get(@PathVariable Long id) {
        return responseDtoMapper.mapToDto(teamService.get(id));
    }

    @PutMapping("/{id}")
    public TeamResponseDto update(@RequestBody TeamRequestDto requestDto,
                                  @PathVariable Long id) {
        Team team = requestDtoMapper.mapToModel(requestDto);
        team.setId(id);
        Team updateTeam = teamService.update(team);
        return responseDtoMapper.mapToDto(updateTeam);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        teamService.delete(id);
    }

    @GetMapping
    public List<TeamResponseDto> getAll() {
        return teamService.findAll()
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}/transfer/{teamToId}")
    public void transferPlayer(@PathVariable Long id, @PathVariable Long teamToId) {
        transferService.transferPlayer(id, teamToId);
    }

    @GetMapping("/{teamId}/players")
    public List<PlayerResponseDto> getPlayersByTeamId(@PathVariable Long teamId) {
        List<Player> allByTeamId = teamService.findAllByTeamId(teamId);
        return allByTeamId.stream()
                .map(playerDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

}
