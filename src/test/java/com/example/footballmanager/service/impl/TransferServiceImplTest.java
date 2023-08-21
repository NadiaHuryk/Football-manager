package com.example.footballmanager.service.impl;

import com.example.footballmanager.exeption.TransferException;
import com.example.footballmanager.model.Player;
import com.example.footballmanager.model.Team;
import com.example.footballmanager.service.PlayerService;
import com.example.footballmanager.service.TeamService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransferServiceImplTest {
    private static TransferServiceImpl transferService;
    private static PlayerService playerService;
    private static TeamService teamService;

    private Team playerTeam;
    private Player player;
    private Team teamTo;

    @BeforeAll
    static void beforeAll() {
        playerService = Mockito.mock(PlayerService.class);
        teamService = Mockito.mock(TeamService.class);
        transferService = new TransferServiceImpl(playerService, teamService);
    }

    @BeforeEach
    void setUp() {
        playerTeam = new Team();
        playerTeam.setId(1L);
        playerTeam.setName("Real Madrid");
        playerTeam.setCountry("Spain");
        playerTeam.setCity("Madrid");
        playerTeam.setCommission(5);
        playerTeam.setBalance(BigDecimal.valueOf(2000000));

        player = new Player();
        player.setId(1L);
        player.setFirstName("Bob");
        player.setLastName("Smith");
        player.setBirthDate(LocalDate.of(1995, 5, 10));
        player.setCareerStartDate(LocalDate.of(2015, 5, 10));
        player.setTeam(playerTeam);

        teamTo = new Team();
        teamTo.setId(2L);
        teamTo.setName("Barcelona");
        teamTo.setCountry("Spain");
        teamTo.setCity("Barcelona");
        teamTo.setCommission(3);
        teamTo.setBalance(BigDecimal.valueOf(3000000));
    }

    @Test
    void testTransferPlayer_Ok() {
        Mockito.when(playerService.get(1L)).thenReturn(player);
        Mockito.when(teamService.get(2L)).thenReturn(teamTo);
        transferService.transferPlayer(1L, 2L);
        assertEquals(teamTo, player.getTeam());
        assertEquals(BigDecimal.valueOf(2353571), playerTeam.getBalance());
        assertEquals(BigDecimal.valueOf(2646429), teamTo.getBalance());
    }

    @Test
    void testTransferPlayerNotEnoughBalance_NotOk() {
        playerTeam.setBalance(BigDecimal.valueOf(10000));
        teamTo.setBalance((BigDecimal.valueOf(1000)));
        Mockito.when(playerService.get(1L)).thenReturn(player);
        Mockito.when(teamService.get(2L)).thenReturn(teamTo);
        Mockito.when(teamService.get(1L)).thenReturn(playerTeam);
        assertThrows(TransferException.class, () -> transferService.transferPlayer(1L, 2L));
    }

    @Test
    void testTransferPlayer_ValidTransfer_PlayerTeamBalanceUpdated() {
        Mockito.when(playerService.get(1L)).thenReturn(player);
        Mockito.when(teamService.get(2L)).thenReturn(teamTo);
        transferService.transferPlayer(1L, 2L);
        assertEquals(BigDecimal.valueOf(2353571), playerTeam.getBalance());
    }

    @Test
    void testTransferPlayer_ValidTransfer_TeamToBalanceUpdated() {
        Mockito.when(playerService.get(1L)).thenReturn(player);
        Mockito.when(teamService.get(2L)).thenReturn(teamTo);
        transferService.transferPlayer(1L, 2L);
        assertEquals(BigDecimal.valueOf(2646429), teamTo.getBalance());
    }
}
