import {Component, OnInit} from '@angular/core';
import {PersonService} from '../../../services/person/person';
import {Observable} from 'rxjs';
import {PersonML} from '../../../models/PersonML';
import {AsyncPipe} from '@angular/common';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-person-details',
    imports: [
        AsyncPipe
    ],
  templateUrl: './person-details.html',
  styleUrl: './person-details.css'
})
export class PersonDetailsComponent implements OnInit {

    person$!: Observable<PersonML>;

    constructor(private route: ActivatedRoute,
                private personService: PersonService) { }

    ngOnInit(): void {
        const id = this.route.snapshot.paramMap.get('id');
        if (id) {
            this.person$ = this.personService.getPersonById(id);
        }
    }
}
