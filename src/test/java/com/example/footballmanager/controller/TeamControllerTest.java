package com.example.footballmanager.controller;

import com.example.footballmanager.dto.mapper.TeamMapper;
import com.example.footballmanager.dto.request.TeamRequestDto;
import com.example.footballmanager.dto.response.TeamResponseDto;
import com.example.footballmanager.model.Team;
import com.example.footballmanager.service.TeamService;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTest extends UtilModelObjects {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @MockBean
    private TeamMapper mapper;

    private final Team team = getTeam();
    private TeamResponseDto teamResponseDto = getTeamResponseDto();

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void createTeam_Ok() {
        Mockito.when(teamService.save(Mockito.any(Team.class))).thenReturn(team);
        Mockito.when(mapper.mapToModel(Mockito.any(TeamRequestDto.class))).thenReturn(team);
        Mockito.when(mapper.mapToDto(Mockito.any(Team.class))).thenReturn(getTeamResponseDto());
        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(getTeamResponseDto())
                .when()
                .post("/teams")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(teamResponseDto.getId().intValue()))
                .body("name", Matchers.equalTo(teamResponseDto.getName()))
                .body("country", Matchers.equalTo(teamResponseDto.getCountry()))
                .body("city", Matchers.equalTo(teamResponseDto.getCity()))
                .body("commission", Matchers.equalTo(teamResponseDto.getCommission()))
                .body("balance", Matchers.equalTo(teamResponseDto.getBalance().intValue()));
    }

    @Test
    void getTeam_Ok() {
        Mockito.when(teamService.get(team.getId())).thenReturn(team);
        Mockito.when(mapper.mapToDto(Mockito.any(Team.class))).thenReturn(teamResponseDto);
        RestAssuredMockMvc
                .when()
                .get("/teams/{id}", team.getId().intValue())
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(teamResponseDto.getId().intValue()))
                .body("name", Matchers.equalTo(teamResponseDto.getName()))
                .body("country", Matchers.equalTo(teamResponseDto.getCountry()))
                .body("city", Matchers.equalTo(teamResponseDto.getCity()))
                .body("commission", Matchers.equalTo(teamResponseDto.getCommission()))
                .body("balance", Matchers.equalTo(teamResponseDto.getBalance().intValue()));
    }

    @Test
    void updateTeam_Ok() {
        Mockito.when(teamService.update(team)).thenReturn(team);
        Mockito.when(mapper.mapToModel(getTeamRequestDto())).thenReturn(team);
        when(mapper.mapToDto(team)).thenReturn(getTeamResponseDto());
        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(getTeamRequestDto())
                .when()
                .put("/teams/{id}", team.getId().intValue())
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(teamResponseDto.getId().intValue()))
                .body("name", Matchers.equalTo(teamResponseDto.getName()))
                .body("country", Matchers.equalTo(teamResponseDto.getCountry()))
                .body("city", Matchers.equalTo(teamResponseDto.getCity()))
                .body("commission", Matchers.equalTo(teamResponseDto.getCommission()))
                .body("balance", Matchers.equalTo(teamResponseDto.getBalance().intValue()));
    }

    @Test
    void deleteTeam_Ok() {
        RestAssuredMockMvc
                .given()
                .when()
                .delete("/teams/{id}", team.getId().intValue())
                .then()
                .statusCode(204);
    }

    @Test
    void getAllTeams_Ok() {
        int countOfTeams = 2;
        List<Team> teams = getTeams(countOfTeams);
        TeamResponseDto createdTeamResponseDto1 = new TeamResponseDto();
        createdTeamResponseDto1.setId(1L);
        createdTeamResponseDto1.setName("Team A");
        createdTeamResponseDto1.setCountry("Ukraine");
        createdTeamResponseDto1.setCity("Lviv");
        createdTeamResponseDto1.setCommission(1);
        createdTeamResponseDto1.setBalance(BigDecimal.valueOf(10000));

        TeamResponseDto createdTeamResponseDto2 = new TeamResponseDto();
        createdTeamResponseDto2.setId(2L);
        createdTeamResponseDto2.setName("Team A");
        createdTeamResponseDto2.setCountry("Ukraine");
        createdTeamResponseDto2.setCity("Lviv");
        createdTeamResponseDto2.setCommission(1);
        createdTeamResponseDto2.setBalance(BigDecimal.valueOf(10000));

        List<TeamResponseDto> teamResponseDtos = new ArrayList<>();
        teamResponseDtos.add(createdTeamResponseDto1);
        teamResponseDtos.add(createdTeamResponseDto2);

        Mockito.when(teamService.findAll()).thenReturn(teams);
        Mockito.when(mapper.mapToDto(Mockito.any(Team.class))).thenReturn(createdTeamResponseDto1,
                createdTeamResponseDto2);
        RestAssuredMockMvc
                .when()
                .get("/teams")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(countOfTeams))
                .body("[0].id", Matchers.equalTo(teams.get(0).getId().intValue()))
                .body("[0].name", Matchers.equalTo(teams.get(0).getName()))
                .body("[0].country", Matchers.equalTo(teams.get(0).getCountry()))
                .body("[0].city", Matchers.equalTo(teams.get(0).getCity()))
                .body("[0].commission", Matchers.equalTo(teams.get(0).getCommission()))
                .body("[0].balance", Matchers.equalTo(teams.get(0).getBalance().intValue()))
                .body("[1].id", Matchers.equalTo(teams.get(1).getId().intValue()))
                .body("[1].name", Matchers.equalTo(teams.get(1).getName()))
                .body("[1].country", Matchers.equalTo(teams.get(1).getCountry()))
                .body("[1].city", Matchers.equalTo(teams.get(1).getCity()))
                .body("[1].commission", Matchers.equalTo(teams.get(1).getCommission()))
                .body("[1].balance", Matchers.equalTo(teams.get(1).getBalance().intValue()));
    }
}
