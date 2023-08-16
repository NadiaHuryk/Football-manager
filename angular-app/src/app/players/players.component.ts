import {Component, OnInit} from '@angular/core';
import {PlayerResponse} from "../model/player.response";
import {PlayersService} from '../service/player.service';
import {PlayerRequest} from "../model/player.request";
import {TeamResponse} from "../model/team.response";
import {TeamsService} from "../service/team.service";
@Component({
  selector: 'app-players',
  templateUrl: './players.component.html',
  styleUrls: ['./players.component.scss']
})
export class PlayersComponent implements OnInit {
  players: PlayerResponse[] = [];
  teams: TeamResponse[] = [];
  newPlayer: PlayerRequest = {
    firstName: '',
    lastName: '',
    birthDate: '',
    careerStartDate: '',
    teamId: 0
  };

  constructor(private playerService: PlayersService, private teamsService: TeamsService) { }

  ngOnInit(): void {
    this.getPlayers();
    this.getTeams();
  }

  getPlayers(): void {
    this.playerService.getPlayers().subscribe(players => this.players = players);
  }

  getTeams(): void {
    this.teamsService.getTeams().subscribe(teams => this.teams = teams);
  }

  addPlayer(): void {
    this.playerService.createPlayer(this.newPlayer).subscribe(newPlayer => {
      this.players.push(newPlayer);
      this.resetNewPlayerForm();
    });
  }

  deletePlayer(id: number): void {
    this.players = this.players.filter(player => player.id !== id);
    this.playerService.deletePlayer(id).subscribe();
  }

  getTeamName(teamId: number): string {
    const team = this.teams.find(team => team.id === teamId);
    return team ? team.name : 'Unknown';
  }

  private resetNewPlayerForm(): void {
    this.newPlayer = {
      firstName: '',
      lastName: '',
      birthDate: '',
      careerStartDate: '',
      teamId: 0
    };
  }
}
