import {Component, OnInit} from '@angular/core';
import { TeamResponse } from '../model/team.response';
import {ActivatedRoute} from "@angular/router";
import {TeamsService} from "../service/team.service";
import {PlayerResponse} from "../model/player.response";
import {PlayersService} from "../service/player.service";

@Component({
  selector: 'app-teams-details',
  templateUrl: './teams-detail.component.html',
  styleUrls: ['./teams-detail.component.scss']
})
export class TeamsDetailComponent implements OnInit {
  team: TeamResponse = {} as TeamResponse;
  players: PlayerResponse[] = [];
  private teamId: number | undefined;
  editMode = false;

  constructor(
    private route: ActivatedRoute,
    private playerService: PlayersService,
    private teamService: TeamsService,
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.teamId = +params['id'];
      if (this.teamId) {
        this.getTeamById(this.teamId);
        this.getPlayersByTeamId(this.teamId);
      }
    });
  }

  getTeamById(teamId: number): void {
    this.teamService.getTeam(teamId).subscribe(team => this.team = team);
  }

  getPlayersByTeamId(teamId: number): void {
    this.teamService.getPlayersByTeamId(teamId).subscribe(players => this.players = players);
  }

  save(): void {
    if (this.team) {
      this.teamService.updateTeam(this.team).subscribe(updatedTeam => {
        this.team = updatedTeam;
        this.editMode = false;
      });
    }
  }

  cancel(): void {
    this.editMode = false;
    if (this.team) {
      // Resetting the team object to discard changes
      this.getTeamById(this.team.id);
    }
  }
}
