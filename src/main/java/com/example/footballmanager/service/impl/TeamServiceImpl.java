package com.example.footballmanager.service.impl;

import com.example.footballmanager.model.Player;
import com.example.footballmanager.model.Team;
import com.example.footballmanager.repository.PlayerRepository;
import com.example.footballmanager.repository.TeamRepository;
import com.example.footballmanager.service.TeamService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    @Override
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team get(Long id) {
        return teamRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Can't find team by id: " + id));
    }

    @Override
    public Team update(Team team) {
        if (teamRepository.existsById(team.getId())) {
            return teamRepository.save(team);
        }
        throw new RuntimeException("Can't find team by id : " + team.getId());
    }

    @Override
    public void delete(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new RuntimeException("Can't find team by id : " + id);
        }
        List<Player> players = playerRepository.findAllByTeamId(id);

        for (Player player : players) {
            player.setTeam(null);
            playerRepository.save(player);
        }
        teamRepository.deleteById(id);
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public List<Player> findAllByTeamId(Long teamId) {
        return playerRepository.findAllByTeamId(teamId);
    }
}
