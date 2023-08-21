package com.example.footballmanager.controller;

import com.example.footballmanager.dto.mapper.PlayerMapper;
import com.example.footballmanager.dto.mapper.TeamMapper;
import com.example.footballmanager.dto.request.TeamRequestDto;
import com.example.footballmanager.dto.response.PlayerResponseDto;
import com.example.footballmanager.dto.response.TeamResponseDto;
import com.example.footballmanager.model.Player;
import com.example.footballmanager.model.Team;
import com.example.footballmanager.service.TeamService;
import com.example.footballmanager.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final TeamMapper mapper;
    private final PlayerMapper playerDtoMapper;
    private final TransferService transferService;

    @PostMapping
    @Operation(summary = "Add team", description = "Create a new team entry in the system")
    public ResponseEntity<TeamResponseDto> create(@Parameter(schema = @Schema(type = "String",
            defaultValue = "{\n"
                    + "  \"name\": \"Dnipro\",\n"
                    + "  \"country\": \"Ukraine\",\n"
                    + "  \"city\": \"Kyiv\",\n"
                    + "  \"commission\": 1,\n"
                    + "  \"balance\": 10000000\n"
                    + "}")) @RequestBody @Valid TeamRequestDto requestDto) {
        Team team = teamService.save(mapper.mapToModel(requestDto));
        return ResponseEntity.ok(mapper.mapToDto(team));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get team by id",
            description = "Retrieve the team information by its id")
    public ResponseEntity<TeamResponseDto> get(@Parameter(description = "Team id", example = "1")
                                                   @PathVariable Long id) {
        TeamResponseDto teamResponseDto = mapper.mapToDto(teamService.get(id));
        return ResponseEntity.ok(teamResponseDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update team by id",
            description = "Update team information by providing the player id")
    public ResponseEntity<TeamResponseDto> update(
            @Parameter(description = "Team id", example = "1")
            @PathVariable Long id,
            @Parameter(schema = @Schema(type = "String",
                    defaultValue = "{\n"
                            + "  \"name\": \"Dnipro\",\n"
                            + "  \"country\": \"Ukraine\",\n"
                            + "  \"city\": \"Lviv\",\n"
                            + "  \"commission\": 2,\n"
                            + "  \"balance\": 10000000\n"
                            + "}")) @RequestBody @Valid TeamRequestDto requestDto) {
        Team team = mapper.mapToModel(requestDto);
        team.setId(id);
        Team updateTeam = teamService.update(team);
        TeamResponseDto teamResponseDto = mapper.mapToDto(updateTeam);
        return ResponseEntity.ok(teamResponseDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete team by id", description = "Delete team by id")
    public void delete(@Parameter(description = "Team id", example = "1")
                           @PathVariable Long id) {
        teamService.delete(id);
    }

    @GetMapping
    @Operation(summary = "Get all teams", description = "List of all teams")
    public ResponseEntity<List<TeamResponseDto>> getAll() {
        List<TeamResponseDto> teamResponseDtos = teamService.findAll()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teamResponseDtos);
    }

    @PostMapping("/{id}/transfer/{teamToId}")
    @Operation(summary = "Transfer player to another team",
            description = "Transfer a player with the specified id "
                    + "to another team with the specified id")
    public ResponseEntity<TeamResponseDto> transferPlayer(
            @Parameter(description = "Player id", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Destination team id", example = "2")
            @PathVariable Long teamToId) {
        transferService.transferPlayer(id, teamToId);
        Team updatedTeam = teamService.get(teamToId);
        TeamResponseDto updatedTeamDto = mapper.mapToDto(updatedTeam);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTeamDto);
    }

    @GetMapping("/{teamId}/players")
    @Operation(summary = "Get players by team id",
            description = "Retrieve a list of players belonging to the team with the specified id")
    public ResponseEntity<List<PlayerResponseDto>> getPlayersByTeamId(
            @Parameter(description = "Team ID", example = "1")
            @PathVariable Long teamId) {
        List<Player> allByTeamId = teamService.findAllByTeamId(teamId);
        List<PlayerResponseDto> playerResponseDtos = allByTeamId.stream()
                .map(playerDtoMapper::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(playerResponseDtos);
    }
}
