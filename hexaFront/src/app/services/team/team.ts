import {inject, Injectable} from '@angular/core';
import {catchError, Observable, of, Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {TeamML} from '../../models/TeamML';
import {UtilsService} from '../utils/utils';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class TeamService {
    readonly API_URL = "http://127.0.0.1:8080/teams";

    private subjectTeamCreated = new Subject<TeamCreatedSupplier>();
    teamCreated$ = this.subjectTeamCreated.asObservable();

    #router: Router = inject(Router);
    #utilsService: UtilsService = inject(UtilsService);
    #http: HttpClient = inject(HttpClient);

    announceTeamCreated(team: TeamML) {
        this.subjectTeamCreated.next({team} as TeamCreatedSupplier);
    }

    createTeam(team: TeamML): Observable<TeamML> {
        return this.#http.post<TeamML>(this.API_URL, team).pipe(
            catchError(error => this.#utilsService.handleError(error))
        );
    }

    getAllTeams(): Observable<TeamML[]> {
        return this.#http.get<TeamML[]>(this.API_URL).pipe(
            catchError(error => this.#utilsService.handleError(error))
        );
    }

    getTeamById(id: string): Observable<TeamML> {
        return this.#http.get<TeamML>(`${this.API_URL}/${id}`).pipe(
            catchError((error) => {
                this.#router.navigate(['/teams']);
                this.#utilsService.handleError(error);
                throw of(null);
            })
        );
    }
}

export interface TeamCreatedSupplier {
    team: TeamML;
}
