package com.example.footballmanager.controller;

import com.example.footballmanager.dto.mapper.PlayerMapper;
import com.example.footballmanager.dto.request.PlayerRequestDto;
import com.example.footballmanager.dto.response.PlayerResponseDto;
import com.example.footballmanager.model.Player;
import com.example.footballmanager.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;
    private final PlayerMapper mapper;

    @PostMapping
    @Operation(summary = "Add player", description = "Create a new player entry in the system")
    public ResponseEntity<PlayerResponseDto> create(@Parameter(schema = @Schema(type = "String",
            defaultValue = "{\n"
                    + "  \"firstName\": \"Bob\",\n"
                    + "  \"lastName\": \"Bobson\",\n"
                    + "  \"birthDate\": \"1990-01-01\",\n"
                    + "  \"careerStartDate\": \"2010-07-14\",\n"
                    + "  \"teamId\": 1\n"
                    + "}")) @RequestBody @Valid PlayerRequestDto playerRequestDto) {
        Player player = playerService.save(mapper.mapToModel(playerRequestDto));
        return ResponseEntity.ok(mapper.mapToDto(player));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get player by id",
            description = "Retrieve the player information by its id")
    public ResponseEntity<PlayerResponseDto> get(@Parameter(description = "Player id", example = "1")
                                                     @PathVariable Long id) {
        PlayerResponseDto playerResponseDto = mapper.mapToDto(playerService.get(id));
        return ResponseEntity.ok(playerResponseDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update player by id",
            description = "Update player information by providing the player id")
    public ResponseEntity<PlayerResponseDto> update(
            @Parameter(description = "Player id", example = "1")
            @PathVariable Long id,
            @Parameter(schema = @Schema(type = "String",
            defaultValue = "{\n"
                    + "  \"firstName\": \"John\",\n"
                    + "  \"lastName\": \"Bobson\",\n"
                    + "  \"birthDate\": \"1990-01-01\",\n"
                    + "  \"careerStartDate\": \"2010-07-14\",\n"
                    + "  \"teamId\": 1\n"
                    + "}")) @RequestBody @Valid PlayerRequestDto playerRequestDto) {
        Player player = mapper.mapToModel(playerRequestDto);
        player.setId(id);
        Player updatedPlayer = playerService.update(player);
        PlayerResponseDto updatedPlayerDto = mapper.mapToDto(updatedPlayer);
        return ResponseEntity.ok(updatedPlayerDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete player by id", description = "Delete player by id")
    public void delete(@Parameter(description = "Player id", example = "1")
                           @PathVariable Long id) {
        playerService.delete(id);
    }

    @GetMapping
    @Operation(summary = "Get all players", description = "List of all players")
    public ResponseEntity<List<PlayerResponseDto>> getAll() {
        List<PlayerResponseDto> playerResponseDtos = playerService.findAll()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(playerResponseDtos);
    }
}
