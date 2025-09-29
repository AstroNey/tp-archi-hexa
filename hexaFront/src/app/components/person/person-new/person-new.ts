import {Component, inject} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {PersonML} from '../../../models/PersonML';
import {PersonService} from '../../../services/person/person';
import {Router} from '@angular/router';

@Component({
  selector: 'app-person-new',
    imports: [
        FormsModule
    ],
  templateUrl: './person-new.html',
  styleUrl: './person-new.css'
})
export class PersonNewComponent {
    private router: Router = inject(Router);
    person$: PersonML = new PersonML();

    constructor(
        private personService: PersonService
    ) {}

    savePerson() {
        this.personService.createPerson({ ...this.person$ }).subscribe({
            next: (created) => {
                this.person$ = new PersonML();
                this.router.navigate(['/persons']);
            },
            error: (err) => console.error('POST error:', err)
        });
    }
}
