import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { PlayerResponse } from '../model/player.response';
import { PlayerRequest } from '../model/player.request';

@Injectable({
  providedIn: 'root'
})
export class PlayersService {
  private playersUrl = 'http://localhost:6868/players';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }


  getPlayers(): Observable<PlayerResponse[]> {
    console.log('Fetching players...');
    return this.http.get<PlayerResponse[]>(this.playersUrl)
      .pipe(
        catchError(this.handleError<PlayerResponse[]>('getPlayers', []))
      );
  }

  getPlayer(id: number): Observable<PlayerResponse> {
    const url = `${this.playersUrl}/${id}`;
    return this.http.get<PlayerResponse>(url).pipe(
      catchError(this.handleError<PlayerResponse>(`getPlayer id=${id}`))
    );
  }

  createPlayer(player: PlayerRequest): Observable<PlayerResponse> {
    return this.http.post<PlayerResponse>(this.playersUrl, player, this.httpOptions)
      .pipe(
        tap((newPlayer: PlayerResponse) => this.log(`added player with id=${newPlayer.id}`)),
        catchError(this.handleError<PlayerResponse>('addPlayer'))
      );
  }

  deletePlayer(id: number): Observable<PlayerResponse> {
    const url = `${this.playersUrl}/${id}`;
    return this.http.delete<PlayerResponse>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted player id=${id}`)),
      catchError(this.handleError<PlayerResponse>('deletePlayer'))
    );
  }

  updatePlayer(player: PlayerResponse): Observable<any> {
    const url = `${this.playersUrl}/${player.id}`;
    return this.http.put(url, player, this.httpOptions).pipe(
      tap(_ => this.log(`updated player id=${player.id}`)),
      catchError(this.handleError<any>('updatePlayer'))
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
    console.log(`PlayersService: ${message}`);
  }
}
