import {Component, inject, OnDestroy, OnInit} from '@angular/core';
import {PersonCreatedSupplier, PersonService} from '../../services/person/person';
import {PersonML} from '../../models/PersonML';
import {RouterLink} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
    selector: 'app-person',
    templateUrl: './person.html',
    imports: [RouterLink],
    styleUrl: './person.css'
})
export class PersonComponent implements OnInit, OnDestroy {

    private personsCreatedSubscription! : Subscription;
    private countPersonSubscription! : Subscription;
    private getAllPersonSubscription! : Subscription;
    protected persons!: PersonML[];

    #personService: PersonService = inject(PersonService);

    page = 1;
    pageLimit = 5;
    totalPages = 3;

    ngOnInit(): void {
        this.initPage();
        this.initPersonCreatedSubscription();
    }

    ngOnDestroy(): void {
        if (this.personsCreatedSubscription) {
            this.personsCreatedSubscription.unsubscribe();
        }
        if (this.countPersonSubscription) {
            this.countPersonSubscription.unsubscribe();
        }
        if (this.getAllPersonSubscription) {
            this.getAllPersonSubscription.unsubscribe();
        }
    }

    initPersonCreatedSubscription(): void {
        this.personsCreatedSubscription = this.#personService.personCreated$.subscribe(
            (response: PersonCreatedSupplier) => {
                this.persons.push(response.person);
            });
    }

    initPage() {
        this.countPersonSubscription = this.#personService.countAllPerson().subscribe(
            (response: number) => {
                this.totalPages = Math.ceil(response / this.pageLimit);
            }
        )
        this.getAllPersonSubscription = this.#personService.getAllPerson(this.pageLimit, this.page).subscribe(
            (response: PersonML[]) => {
                this.persons = response
            }
        );
    }

    nextPage() {
        if (this.page < this.totalPages) {
            this.page++;
            this.initPage();
        }
    }

    prevPage() {
        if (this.page > 1) {
            this.page--;
            this.initPage();
        }
    }
}
