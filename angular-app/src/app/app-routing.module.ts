import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TeamsComponent} from "./teams/teams.component";
import {PlayersComponent} from "./players/players.component";
import {TeamsDetailComponent} from "./teams-detail/teams-detail.component";
import {PlayersDetailComponent} from "./players-datail/players-detail.component";

const routes: Routes = [
  { path: 'teams', component: TeamsComponent },
  { path: 'players', component: PlayersComponent },
  { path: '', redirectTo: '/teams', pathMatch: 'full' },
  { path: 'teams/:id', component: TeamsDetailComponent },
  { path: 'players/:id', component: PlayersDetailComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
