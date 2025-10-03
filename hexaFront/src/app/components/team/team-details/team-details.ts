import {Component, inject, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {TeamML} from '../../../models/TeamML';
import {TeamService} from '../../../services/team/team';

@Component({
  selector: 'app-team-details',
    imports: [],
  templateUrl: './team-details.html',
  styleUrl: './team-details.css'
})
export class TeamDetailsComponent implements OnInit, OnDestroy {

    private teamSubscription!: Subscription;
    team!: TeamML;

    #route: ActivatedRoute = inject(ActivatedRoute);
    #teamService: TeamService = inject(TeamService);

    ngOnInit(): void {
        const id = this.#route.snapshot.paramMap.get('id');
        if (id) {
            this.teamSubscription = this.#teamService.getTeamById(id).subscribe(
                t => this.team = t
            );
        }
    }

    ngOnDestroy() {
        this.teamSubscription.unsubscribe();
    }
}
