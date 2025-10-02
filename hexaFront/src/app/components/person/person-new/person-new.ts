import {Component, inject} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PersonML} from '../../../models/PersonML';
import {PersonService} from '../../../services/person/person';
import {Router} from '@angular/router';
import {InputTextModule} from 'primeng/inputtext';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-person-new',
    imports: [
        FormsModule,
        InputTextModule,
        ReactiveFormsModule,
    ],
  templateUrl: './person-new.html',
  styleUrl: './person-new.css'
})
export class PersonNewComponent {
    private router: Router = inject(Router);
    person: PersonML = new PersonML();

    #messageService = inject(MessageService);
    #personService = inject(PersonService);

    savePerson() {
        this.#personService.createPerson(this.person).subscribe((response) => {
            this.#personService.announcePersonCreated(response);
            this.router.navigate(['/persons']);
            this.#messageService.add({
                severity:'success',
                summary: 'Success',
                detail: 'Person created successfully'
            });
            this.person = new PersonML();
        });
    }
}
