import {Component, OnDestroy, OnInit} from '@angular/core';
import {PersonCreatedSupplier, PersonService} from '../../services/person/person';
import {PersonML} from '../../models/PersonML';
import {RouterLink} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
    selector: 'app-person',
    templateUrl: './person.html',
    imports: [ RouterLink],
    styleUrl: './person.css'
})
export class PersonComponent implements OnInit, OnDestroy {

    private personsSubscription! : Subscription;
    protected persons!: PersonML[];

    constructor(private personService: PersonService) {

    }

    ngOnInit(): void {
        this.initData();
        this.initPersonCreatedSubscription();
    }

    ngOnDestroy(): void {
        if (this.personsSubscription) {
            this.personsSubscription.unsubscribe();
        }
    }

    initData(): void {
        this.personsSubscription = this.personService.getAllPerson().subscribe(
            (response: PersonML[]) => this.persons = response
        );
    }

    initPersonCreatedSubscription(): void {
        this.personsSubscription = this.personService.personCreated$.subscribe(
            (response: PersonCreatedSupplier) => {
                this.persons.push(response.person);
            });
    }
}
