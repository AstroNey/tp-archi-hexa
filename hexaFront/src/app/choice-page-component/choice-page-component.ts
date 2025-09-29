import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-choice-page',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './choice-page-component.html',
    styleUrls: ['./choice-page-component.scss'],
})
export class ChoicePageComponent {
    constructor(private router: Router) {}

    onTeamSelect(): void {
        console.log('Team selected');
        this.router.navigate(['/team-dashboard']);
    }

    onPersonSelect(): void {
        console.log('Person selected');
        this.router.navigate(['/person-dashboard']);
    }
}
