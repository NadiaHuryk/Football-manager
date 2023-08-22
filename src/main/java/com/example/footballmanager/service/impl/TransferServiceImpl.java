package com.example.footballmanager.service.impl;

import com.example.footballmanager.exeption.TransferException;
import com.example.footballmanager.model.Player;
import com.example.footballmanager.model.Team;
import com.example.footballmanager.service.PlayerService;
import com.example.footballmanager.service.TeamService;
import com.example.footballmanager.service.TransferService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransferServiceImpl implements TransferService {
    private static final BigDecimal TRANSFER_BASE_RATE = BigDecimal.valueOf(100000);
    private static final BigDecimal TRANSFER_COMMISSION_RATE = BigDecimal.valueOf(100);
    private final PlayerService playerService;
    private final TeamService teamService;

    @Override
    @Transactional
    public void transferPlayer(Long playerId, Long teamToId) {
        Player player = playerService.get(playerId);
        Team playerTeam = player.getTeam();
        Team teamTo = teamService.get(teamToId);
        BigDecimal calculatedCost = calculateCost(player, playerTeam);
        validateTransfer(teamTo, calculatedCost);
        playerTeam.setBalance(playerTeam.getBalance().add(calculatedCost));
        teamTo.setBalance(teamTo.getBalance().subtract(calculatedCost));
        player.setTeam(teamTo);
        playerService.update(player);
        teamService.update(playerTeam);
        teamService.update(teamTo);
    }

    private BigDecimal calculateCost(Player player, Team teamFrom) {
        long monthsExperience = ChronoUnit.MONTHS.between(player.getCareerStartDate(),
                LocalDate.now());
        int yearsDifference = LocalDate.now().getYear() - player.getBirthDate().getYear();
        BigDecimal yearsBigDecimal = BigDecimal.valueOf(yearsDifference);
        BigDecimal priceTransfer = TRANSFER_BASE_RATE.multiply(BigDecimal.valueOf(monthsExperience))
                .divide(yearsBigDecimal, RoundingMode.HALF_UP);
        BigDecimal teamCommission = BigDecimal.valueOf(teamFrom.getCommission());
        BigDecimal commissionMultiplier = teamCommission.divide(TRANSFER_COMMISSION_RATE,
                RoundingMode.HALF_UP);
        BigDecimal multiply = priceTransfer.multiply(commissionMultiplier);
        return priceTransfer.add(multiply);
    }

    private void validateTransfer(Team teamTo, BigDecimal cost) {
        if (teamTo.getBalance().compareTo(cost) < 0) {
            throw new TransferException("Can't make transfer because not enough money on team "
                    + teamTo.getName() + " balance");
        }
    }
}
