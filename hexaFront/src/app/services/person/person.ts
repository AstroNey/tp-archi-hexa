import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, Observable, of, Subject} from 'rxjs';
import {PersonML} from '../../models/PersonML';
import {UtilsService} from '../utils/utils';
import {Router} from '@angular/router';

@Injectable({providedIn: 'root'})
export class PersonService {
    readonly API_URL = "http://127.0.0.1:8080/persons";

    private subjectPersonCreated = new Subject<PersonCreatedSupplier>();
    personCreated$ = this.subjectPersonCreated.asObservable();

    #router: Router = inject(Router);
    #utilsService: UtilsService = inject(UtilsService);
    #http: HttpClient = inject(HttpClient);

    announcePersonCreated(person: PersonML) {
        this.subjectPersonCreated.next({person} as PersonCreatedSupplier);
    }

    createPerson(person: PersonML): Observable<PersonML> {
        return this.#http.post<PersonML>(this.API_URL, person).pipe(
            catchError(error => this.#utilsService.handleError(error))
        );
    }

    getAllPerson(): Observable<PersonML[]> {
        return this.#http.get<PersonML[]>(this.API_URL).pipe(
            catchError(error => this.#utilsService.handleError(error))
        );
    }

    getPersonById(id: string): Observable<PersonML> {
        return this.#http.get<PersonML>(`${this.API_URL}/${id}`).pipe(
            catchError(error => {
                this.#router.navigate(['/persons']);
                this.#utilsService.handleError(error);
                return of(error);
            })
        );
    }

}

export interface PersonCreatedSupplier {
    person: PersonML;
}
