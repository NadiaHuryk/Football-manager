import {Component, OnInit} from '@angular/core';
import { TeamResponse } from '../model/team.response';
import {TeamsService} from "../service/team.service";
import {TeamRequest} from "../model/team.request";

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.scss'],
})
export class TeamsComponent implements OnInit {
  teams: TeamResponse[] = [];

  newTeam: TeamRequest = {
    name: '',
    country: '',
    city: '',
    commission: 0,
    balance: 0,
    playerIds: []
  };

  constructor(private teamsService: TeamsService) {}

  ngOnInit() {
    this.getTeams();
  }

  getTeams(): void {
    this.teamsService.getTeams().subscribe(teams => (this.teams = teams));
  }
  addTeam(): void {
    this.teamsService.addTeam(this.newTeam).subscribe(newTeam => {
      this.teams.push(newTeam);
      this.resetNewTeamForm();
    });
  }

  deleteTeam(id: number): void {
    this.teams = this.teams.filter(team => team.id !== id);
    this.teamsService.deleteTeam(id).subscribe();
  }

  private resetNewTeamForm(): void {
    this.newTeam = {
      name: '',
      country: '',
      city: '',
      commission: 0,
      balance: 0,
      playerIds: []
    };
  }
}
