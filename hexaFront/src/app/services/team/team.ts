import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable, tap} from 'rxjs';
import {PersonML} from '../../models/PersonML';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class Team {
    readonly API_URL = "http://127.0.0.1:8080/teams";

    private teamsSubject = new BehaviorSubject<TeamML[]>([]);
    teams$: Observable<PersonML[]> = this.teamsSubject.asObservable();

    constructor(private http: HttpClient) {
    }

    createTeam(team$: TeamML): Observable<TeamML> {
        console.log('Payload going to service:', team$);
        return this.http.post<TeamML>(this.API_URL, team$)
            .pipe(
                tap(teamCreated => {
                    const current = this.teamsSubject.getValue();
                    this.teamsSubject.next([...current, teamCreated]);
                })
        );
    }

    getAllTeams(): void {
        this.http.get<TeamML[]>(this.API_URL).subscribe(teams => {
            this.teamsSubject.next(teams);
        });
    }

    getTeamById(id: string): Observable<TeamML> {
        return this.http.get<TeamML>(`${this.API_URL}/${id}`);
    }
}
