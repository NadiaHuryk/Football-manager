package com.example.footballmanager.controller;

import com.example.footballmanager.dto.mapper.Mapper;
import com.example.footballmanager.dto.request.PlayerRequestDto;
import com.example.footballmanager.dto.response.PlayerResponseDto;
import com.example.footballmanager.model.Player;
import com.example.footballmanager.service.PlayerService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
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
    private final Mapper<Player, PlayerRequestDto, PlayerResponseDto> mapper;

    @PostMapping
    public PlayerResponseDto create(@RequestBody PlayerRequestDto playerRequestDto) {
        Player player = playerService.save(mapper.mapToModel(playerRequestDto));
        return mapper.mapToDto(player);
    }

    @GetMapping("/{id}")
    public PlayerResponseDto get(@PathVariable Long id) {
        return mapper.mapToDto(playerService.get(id));
    }

    @PutMapping("/{id}")
    public PlayerResponseDto update(@RequestBody PlayerRequestDto playerRequestDto,
                                  @PathVariable Long id) {
        Player player = mapper.mapToModel(playerRequestDto);
        player.setId(id);
        Player updatePlayer = playerService.update(player);
        return mapper.mapToDto(updatePlayer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        playerService.delete(id);
    }

    @GetMapping
    public List<PlayerResponseDto> getAll() {
        return playerService.findAll()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
}
