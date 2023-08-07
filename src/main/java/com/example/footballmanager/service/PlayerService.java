package com.example.footballmanager.service;

import com.example.footballmanager.model.Player;
import java.util.List;

public interface PlayerService {
    Player save(Player player);

    Player get(Long id);

    Player update(Player player);

    void delete(Long id);

    List<Player> findAll();
}
