import {Component, OnInit} from '@angular/core';
import {PersonService} from '../../services/person/person';
import {Observable} from 'rxjs';
import {PersonML} from '../../models/PersonML';
import {AsyncPipe} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
    selector: 'app-person',
    templateUrl: './person.html',
    imports: [
        AsyncPipe,
        RouterLink
    ],
    styleUrl: './person.css'
})
export class PersonComponent implements OnInit {

    persons$!: Observable<PersonML[]>;

    constructor(private personService: PersonService) { }

    ngOnInit(): void {
        this.persons$ = this.personService.persons$;

        this.personService.getAllperson();
    }
}
