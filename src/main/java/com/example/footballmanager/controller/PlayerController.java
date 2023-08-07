package com.example.footballmanager.controller;

import com.example.footballmanager.dto.mapper.RequestDtoMapper;
import com.example.footballmanager.dto.mapper.ResponseDtoMapper;
import com.example.footballmanager.dto.request.PlayerRequestDto;
import com.example.footballmanager.dto.response.PlayerResponseDto;
import com.example.footballmanager.model.Player;
import com.example.footballmanager.service.PlayerService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@RestController
@RequestMapping("/players")
@CrossOrigin(origins = "http://localhost:4200",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class PlayerController {
    private final PlayerService playerService;
    private final RequestDtoMapper<PlayerRequestDto, Player> requestDtoMapper;
    private final ResponseDtoMapper<PlayerResponseDto, Player> responseDtoMapper;

    @PostMapping
    public PlayerResponseDto create(@RequestBody PlayerRequestDto playerRequestDto) {
        Player player = playerService.save(requestDtoMapper.mapToModel(playerRequestDto));
        return responseDtoMapper.mapToDto(player);
    }

    @GetMapping("/{id}")
    public PlayerResponseDto get(@PathVariable Long id) {
        return responseDtoMapper.mapToDto(playerService.get(id));
    }

    @PutMapping("/{id}")
    public PlayerResponseDto update(@RequestBody PlayerRequestDto playerRequestDto,
                                  @PathVariable Long id) {
        Player player = requestDtoMapper.mapToModel(playerRequestDto);
        player.setId(id);
        Player updatePlayer = playerService.update(player);
        return responseDtoMapper.mapToDto(updatePlayer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        playerService.delete(id);
    }

    @GetMapping
    public List<PlayerResponseDto> getAll() {
        return playerService.findAll()
                .stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
