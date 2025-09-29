import {Component, inject, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {PersonML} from '../../../models/PersonML';
import {TeamML} from '../../../models/TeamML';
import {TeamService} from '../../../services/team/team';
import {PersonService} from '../../../services/person/person';
import {AsyncPipe} from '@angular/common';

@Component({
  selector: 'app-team-new',
    imports: [
        FormsModule,
        ReactiveFormsModule,
        AsyncPipe
    ],
  templateUrl: './team-new.html',
  styleUrl: './team-new.css'
})
export class TeamNewComponent implements OnInit {
    private router: Router = inject(Router);
    team: TeamML = new TeamML();

    allPersonsAddable: PersonML[] = [];

    constructor(
        private teamService: TeamService,
        public personService: PersonService
    ) {
    }

    ngOnInit(): void {
        this.personService.getAllperson();
    }

    saveTeam() {
        this.teamService.createTeam({ ...this.team }).subscribe({
            next: (created) => {
                this.team = new TeamML();
                this.router.navigate(['/teams']);
            },
            error: (err) => console.error('POST error:', err)
        });
    }

    addPerson(value: string) {
        console.log('Value selected to add:', value);
        if (value) {
            if (!this.team.persons) {
                this.team.persons = [];
            }

            this.personService.persons$.subscribe(person => {
                this.allPersonsAddable = person;
            });
            const personToAdd: PersonML | undefined = this.allPersonsAddable.find((person) => {
                return person.id === Number(value)
            });
            this.allPersonsAddable = this.allPersonsAddable.filter((person) => person.id !== Number(value));

            if (personToAdd) {
                this.team.persons.push(personToAdd);
            }
        }
    }
}
