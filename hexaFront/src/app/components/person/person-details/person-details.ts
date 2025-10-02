import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {PersonML} from '../../../models/PersonML';
import {ActivatedRoute} from '@angular/router';
import {PersonService} from '../../../services/person/person';

@Component({
    selector: 'app-person-details',
    imports: [],
    templateUrl: './person-details.html',
    styleUrl: './person-details.css'
})
export class PersonDetailsComponent implements OnInit, OnDestroy {

    #personService = Inject(PersonService);
    #route = Inject(ActivatedRoute);

    private personSubscription! : Subscription;
    person!: PersonML;
    ngOnInit(): void {
        const id = this.#route.snapshot.paramMap.get('id');
        if (id) {
            this.personSubscription = this.#personService.getPersonById(id)
                .subscribe((res: PersonML)=> {
                    if (res) {
                        this.person = res;
                    }
                });
        }
    }

    ngOnDestroy(): void {
        this.personSubscription.unsubscribe();
    }
}
