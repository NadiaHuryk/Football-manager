package com.example.footballmanager.service.impl;

import com.example.footballmanager.exeption.TeamException;
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
        return teamRepository.findById(id).orElseThrow(() ->
                new TeamException("Can't find team by id: " + id));
    }

    @Override
    public Team update(Team team) {
        return teamRepository.findById(team.getId())
                .map(existingTeam -> teamRepository.save(team))
                .orElseThrow(() -> new TeamException("Can't find team by id: " + team.getId()));
    }

    @Override
    public void delete(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new TeamException("Can't find team by id : " + id));

        List<Player> players = playerRepository.findAllByTeamId(id);
        players.forEach(player -> player.setTeam(null));

        playerRepository.saveAll(players);
        teamRepository.delete(team);
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
