import {Component,OnInit} from '@angular/core';
import {PlayerResponse } from '../model/player.response';
import {ActivatedRoute} from "@angular/router";
import {PlayersService} from "../service/player.service";
import {TeamResponse} from "../model/team.response";
import {TeamsService} from "../service/team.service";

@Component({
  selector: 'app-players-detail',
  templateUrl: './players-detail.component.html',
  styleUrls: ['./players-detail.component.scss']
})
export class PlayersDetailComponent implements OnInit {
  player: PlayerResponse = {} as PlayerResponse;
  teams: TeamResponse[] = [];
  editMode = false;
  selectedTeamId = 0;
  notificationMessage = '';
  showNotification = false;
  notificationShown = false;
  errorNotificationShown = false;
  constructor(
    private route: ActivatedRoute,
    private playerService: PlayersService,
    private teamsService: TeamsService,

  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const playerId = +params['id'];
      if (playerId) {
        this.getPlayerById(playerId);
        this.getTeams();
      }
    });
  }

  getTeams(): void {
    this.teamsService.getTeams().subscribe(teams => this.teams = teams);
  }

  getPlayerById(playerId: number): void {
    this.playerService.getPlayer(playerId).subscribe(player => {
      this.player = player;
    });
  }

  getTeamName(teamId: number): string {
    const team = this.teams.find((team) => team.id === teamId);
    return team ? team.name : 'Unknown';
  }
  transfer(playerId: number, newTeamId: number): void {
    if (!this.notificationShown) {
      this.teamsService.transferPlayer(playerId, newTeamId).subscribe(
        transferredPlayer => {
          this.player = transferredPlayer;
          this.notificationMessage = 'Player transferred successfully.';

          this.showNotification = true;
          this.notificationShown = true;
          this.errorNotificationShown = false;

          this.getPlayerById(playerId);

          setTimeout(() => {
            this.showNotification = false;
            this.notificationShown = false;
          }, 3000);
        },
        error => {
          this.notificationMessage = 'Insufficient funds for transfer.';

          this.showNotification = true;
          this.notificationShown = true;
          this.errorNotificationShown = true;

          setTimeout(() => {
            this.showNotification = false;
            this.notificationShown = false;
            this.errorNotificationShown = false;
          }, 3000);
        }
      );
    }
  }

  save(): void {
    if (this.player) {
      this.playerService.updatePlayer(this.player).subscribe(updatedPlayer => {
        this.player = updatedPlayer;
        this.editMode = false;
      });
    }
  }

  cancel(): void {
    this.editMode = false;
    if (this.player) {
      // Reload player details to discard changes
      this.getPlayerById(this.player.id);
    }
  }
}
