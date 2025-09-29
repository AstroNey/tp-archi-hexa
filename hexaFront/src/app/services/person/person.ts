import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable, tap} from 'rxjs';
import {PersonML} from '../../models/PersonML';

@Injectable({providedIn: 'root'})
export class PersonService {
    readonly API_URL = "http://127.0.0.1:8080/persons";

    private personsSubject = new BehaviorSubject<PersonML[]>([]);
    persons$: Observable<PersonML[]> = this.personsSubject.asObservable();

    constructor(private http: HttpClient) {
    }

    createPerson(person$: PersonML): Observable<PersonML> {
        console.log('Payload going to service:', person$);
        return this.http.post<PersonML>(this.API_URL, person$)
            .pipe(
                tap(personCreated => {
                    const current = this.personsSubject.getValue();
                    this.personsSubject.next([...current, personCreated]);
                })
        );
    }

    getAllperson(): void {
        this.http.get<PersonML[]>(this.API_URL).subscribe(persons => {
            this.personsSubject.next(persons);
        });
    }

    getPersonById(id: string): Observable<PersonML> {
        return this.http.get<PersonML>(`${this.API_URL}/${id}`);
    }

}
