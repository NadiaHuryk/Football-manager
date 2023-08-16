package com.example.footballmanager.controller;

import com.example.footballmanager.FootballmanagerApplication;
import com.example.footballmanager.dto.mapper.impl.PlayerMapper;
import com.example.footballmanager.dto.request.PlayerRequestDto;
import com.example.footballmanager.dto.response.PlayerResponseDto;
import com.example.footballmanager.model.Player;
import com.example.footballmanager.service.PlayerService;
import com.example.footballmanager.util.UtilModelObjects;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FootballmanagerApplication.class)
@AutoConfigureMockMvc
class PlayerControllerTest extends UtilModelObjects {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private PlayerMapper mapper;

    private final Player player = getPlayer();
    private final PlayerResponseDto playerResponseDto = getPlayerResponseDto();

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void createPlayer_Ok() {
        Mockito.when(playerService.save(player)).thenReturn(player);
        Mockito.when(mapper.mapToModel(Mockito.any(PlayerRequestDto.class))).thenReturn(player);
        Mockito.when(mapper.mapToDto(Mockito.any(Player.class))).thenReturn(getPlayerResponseDto());
        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(getPlayerRequestDto())
                .when()
                .post("/players")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(playerResponseDto.getId().intValue()))
                .body("firstName", Matchers.equalTo(playerResponseDto.getFirstName()))
                .body("lastName", Matchers.equalTo(playerResponseDto.getLastName()))
                .body("birthDate", Matchers.equalTo(playerResponseDto.getBirthDate().toString()))
                .body("careerStartDate", Matchers.equalTo(playerResponseDto.getCareerStartDate()
                        .toString()))
                .body("teamId", Matchers.equalTo(playerResponseDto.getTeamId().intValue()));
    }

    @Test
    void getPlayerById_Ok() {
        Mockito.when(playerService.get(player.getId())).thenReturn(player);
        Mockito.when(mapper.mapToDto(player)).thenReturn(playerResponseDto);
        RestAssuredMockMvc
                .given()
                .when()
                .get("/players/{id}", player.getId().intValue())
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(playerResponseDto.getId().intValue()))
                .body("firstName", Matchers.equalTo(playerResponseDto.getFirstName()))
                .body("lastName", Matchers.equalTo(playerResponseDto.getLastName()))
                .body("birthDate", Matchers.equalTo(playerResponseDto.getBirthDate().toString()))
                .body("careerStartDate", Matchers.equalTo(playerResponseDto.getCareerStartDate()
                        .toString()))
                .body("teamId", Matchers.equalTo(playerResponseDto.getTeamId().intValue()));
    }

    @Test
    void updatePlayer_Ok() {
        Mockito.when(playerService.update(player)).thenReturn(player);
        Mockito.when(mapper.mapToModel(getPlayerRequestDto())).thenReturn(player);
        Mockito.when(mapper.mapToDto(player)).thenReturn(playerResponseDto);
        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(getPlayerRequestDto())
                .when()
                .put("/players/{id}", player.getId().intValue())
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(playerResponseDto.getId().intValue()))
                .body("firstName", Matchers.equalTo(playerResponseDto.getFirstName()))
                .body("lastName", Matchers.equalTo(playerResponseDto.getLastName()))
                .body("birthDate", Matchers.equalTo(playerResponseDto.getBirthDate().toString()))
                .body("careerStartDate", Matchers.equalTo(playerResponseDto.getCareerStartDate()
                        .toString()))
                .body("teamId", Matchers.equalTo(playerResponseDto.getTeamId().intValue()));
    }

    @Test
    void deletePlayer_Ok() {
        RestAssuredMockMvc
                .given()
                .when()
                .delete("/players/{id}", player.getId().intValue())
                .then()
                .statusCode(200);
    }

    @Test
    void getAllPlayers_Ok() {
        int countOfPlayers = 2;
        List<Player> players = getPlayers(countOfPlayers);
        Mockito.when(playerService.findAll()).thenReturn(players);
        Mockito.when(mapper.mapToDto(Mockito.any(Player.class))).thenCallRealMethod();
        RestAssuredMockMvc
                .given()
                .when()
                .get("/players")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(countOfPlayers))
                .body("[0].id", Matchers.equalTo(players.get(0).getId().intValue()))
                .body("[0].firstName", Matchers.equalTo(players.get(0).getFirstName()))
                .body("[0].lastName", Matchers.equalTo(players.get(0).getLastName()))
                .body("[0].birthDate", Matchers.equalTo(players.get(0).getBirthDate().toString()))
                .body("[0].careerStartDate", Matchers.equalTo(players.get(0).getCareerStartDate()
                        .toString()))
                .body("[0].teamId", Matchers.equalTo(players.get(0).getTeam().getId().intValue()))
                .body("[1].id", Matchers.equalTo(players.get(1).getId().intValue()))
                .body("[1].firstName", Matchers.equalTo(players.get(1).getFirstName()))
                .body("[1].lastName", Matchers.equalTo(players.get(1).getLastName()))
                .body("[1].birthDate", Matchers.equalTo(players.get(1).getBirthDate().toString()))
                .body("[1].careerStartDate", Matchers.equalTo(players.get(1).getCareerStartDate()
                        .toString()))
                .body("[1].teamId", Matchers.equalTo(players.get(1).getTeam().getId().intValue()));
    }
}
