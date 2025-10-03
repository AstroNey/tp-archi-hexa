import {Component, inject, OnDestroy, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {PersonML} from '../../../models/PersonML';
import {TeamML} from '../../../models/TeamML';
import {TeamService} from '../../../services/team/team';
import {PersonService} from '../../../services/person/person';
import {Subscription} from 'rxjs';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-team-new',
    imports: [
        FormsModule,
        ReactiveFormsModule
    ],
  templateUrl: './team-new.html',
  styleUrl: './team-new.css'
})
export class TeamNewComponent implements OnInit, OnDestroy {
    #messageService = inject(MessageService);
    #router: Router = inject(Router);
    #teamService = inject(TeamService);
    #personService = inject(PersonService);

    team: TeamML = new TeamML();

    private personSubscription!: Subscription;
    private teamSubscription!: Subscription;

    persons!: PersonML[];

    ngOnInit(): void {
        this.personSubscription = this.#personService.getAllPerson().subscribe(
            p => this.persons = p.sort((a, b) => (a.name ?? "").localeCompare(b.name ?? ""))
        );
    }

    ngOnDestroy(): void {
        this.personSubscription.unsubscribe();
        if (this.teamSubscription) {
            this.teamSubscription.unsubscribe();
        }
    }

    saveTeam() {
        this.teamSubscription = this.#teamService.createTeam(this.team).subscribe((response) => {
            this.#teamService.announceTeamCreated(response);
            this.#router.navigate(['/teams']);
            this.#messageService.add({
                severity:'success',
                summary: 'Success',
                detail: 'Person created successfully'
            });
            this.team = new TeamML();
        });
    }

    deletePersonInNewTeam(value: number){
        const personToDelete: PersonML | undefined = this.team.persons.find(
            (person) => {
                return person.id === value
            }
        );
        if (personToDelete) {
            this.persons.push(personToDelete);
            this.persons.sort((a, b) => (a.name ?? "").localeCompare(b.name ?? ""));
        }
        console.log(this.persons);
        this.team.persons = this.team.persons.filter(person => person.id !== value);
    }

    addPerson(value: string) {
        if (value) {
            if (!this.team.persons) {
                this.team.persons = [];
            }
            const personToAdd: PersonML | undefined = this.persons.find(
                (person) => {
                    return person.id === Number(value)
                }
            );
            this.persons = this.persons.filter(
                (person) => person.id !== Number(value)
            );

            if (personToAdd) {
                this.team.persons.push(personToAdd);
            }
        }
    }
}
