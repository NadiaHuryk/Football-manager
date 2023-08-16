package com.example.footballmanager.service.impl;

import com.example.footballmanager.exeption.PlayerException;
import com.example.footballmanager.model.Player;
import com.example.footballmanager.repository.PlayerRepository;
import com.example.footballmanager.service.PlayerService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private PlayerRepository playerRepository;

    @Override
    public Player save(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player get(Long id) {
        return playerRepository.findById(id).orElseThrow(() ->
                new PlayerException("Can't find player by id : " + id));
    }

    @Override
    public Player update(Player player) {
        if (playerRepository.existsById(player.getId())) {
            return playerRepository.save(player);
        }
        throw new PlayerException("Can't find player by id : " + player.getId());
    }

    @Override
    public void delete(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }
}
