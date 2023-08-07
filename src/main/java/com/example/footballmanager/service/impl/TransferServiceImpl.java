package com.example.footballmanager.service.impl;

import com.example.footballmanager.model.Player;
import com.example.footballmanager.model.Team;
import com.example.footballmanager.service.PlayerService;
import com.example.footballmanager.service.TeamService;
import com.example.footballmanager.service.TransferService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final PlayerService playerService;
    private final TeamService teamService;

    @Override
    public void transferPlayer(Long playerId, Long teamToId) {
        Player player = playerService.get(playerId);
        Team playerTeam = player.getTeam();
        Team teamTo = teamService.get(teamToId);
        BigDecimal calculatedCost = calculateCost(player, playerTeam);
        validateTransfer(teamTo, calculatedCost);
        playerTeam.getPlayers().remove(player);
        teamTo.getPlayers().add(player);
        playerTeam.setBalance(playerTeam.getBalance().add(calculatedCost));
        teamTo.setBalance(teamTo.getBalance().subtract(calculatedCost));
        teamService.update(playerTeam);
        teamService.update(teamTo);
    }

    private BigDecimal calculateCost(Player player, Team teamFrom) {
        long monthsExperience = ChronoUnit.MONTHS.between(player.getCareerStartDate(),
                LocalDate.now());
        BigDecimal priceTransfer = BigDecimal.valueOf(monthsExperience * 100000
                / (LocalDate.now().getYear() - player.getBirthDate().getYear()));
        BigDecimal multiply = priceTransfer.multiply(BigDecimal.valueOf(teamFrom.getCommission()
                / 100));
        return multiply.add(priceTransfer);
    }

    public void validateTransfer(Team teamTo, BigDecimal cost) {
        if (teamTo.getBalance().compareTo(cost) < 0) {
            throw new RuntimeException("Can't make transfer because not enough money on team "
                    + teamTo.getName() + " balance");
        }
    }
}
