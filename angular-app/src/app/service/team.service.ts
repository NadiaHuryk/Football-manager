import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { TeamResponse } from '../model/team.response';
import { TeamRequest } from '../model/team.request';
import {PlayerResponse} from "../model/player.response";

@Injectable({
  providedIn: 'root'
})
export class TeamsService {
  private teamsUrl = 'http://localhost:6868/teams';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getTeams(): Observable<TeamResponse[]> {
    return this.http.get<TeamResponse[]>(this.teamsUrl)
      .pipe(
        tap(_ => this.log('fetched teams')),
        catchError(this.handleError<TeamResponse[]>('getTeams', []))
      );
  }

  getTeam(id: number): Observable<TeamResponse> {
    const url = `${this.teamsUrl}/${id}`;
    return this.http.get<TeamResponse>(url).pipe(
      tap(_ => this.log(`fetched team id=${id}`)),
      catchError(this.handleError<TeamResponse>(`getTeam id=${id}`))
    );
  }

  addTeam(team: TeamRequest): Observable<TeamResponse> {
    return this.http.post<TeamResponse>(this.teamsUrl, team, this.httpOptions)
      .pipe(
        tap((newTeam: TeamResponse) => this.log(`added team with id=${newTeam.id}`)),
        catchError(this.handleError<TeamResponse>('addTeam'))
      );
  }

  deleteTeam(id: number): Observable<TeamResponse> {
    const url = `${this.teamsUrl}/${id}`;
    return this.http.delete<TeamResponse>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted team id=${id}`)),
      catchError(this.handleError<TeamResponse>('deleteTeam'))
    );
  }

  updateTeam(team: TeamResponse): Observable<any> {
    const url = `${this.teamsUrl}/${team.id}`;
    return this.http.put(url, team, this.httpOptions).pipe(
      tap(_ => this.log(`updated team id=${team.id}`)),
      catchError(this.handleError<any>('updateTeam'))
    );
  }

  transferPlayer(id: number, teamToId: number): Observable<any> {
    const url = `${this.teamsUrl}/${id}/transfer/${teamToId}`;
    return this.http.post(url, this.httpOptions).pipe(
      tap(_ => this.log(`transferred player from team id=${id} to team id=${teamToId}`)),
      catchError(this.handleError<any>('transferPlayer'))
    );
  }

  getPlayersByTeamId(teamId: number): Observable<PlayerResponse[]> {
    const url = `${this.teamsUrl}/${teamId}/players`;
    return this.http.get<PlayerResponse[]>(url)
      .pipe(
        tap(_ => this.log(`fetched players for team id=${teamId}`)),
        catchError(this.handleError<PlayerResponse[]>('getPlayersByTeamId', []))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

  private log(message: string) {
    console.log(`TeamsService: ${message}`);
  }
}
