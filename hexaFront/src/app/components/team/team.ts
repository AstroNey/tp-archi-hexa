import {Component, inject, OnDestroy, OnInit} from '@angular/core';
import {RouterLink} from '@angular/router';
import {Subscription} from 'rxjs';
import {TeamML} from '../../models/TeamML';
import {TeamCreatedSupplier, TeamService} from '../../services/team/team';

@Component({
  selector: 'app-team',
    imports: [
        RouterLink
    ],
  templateUrl: './team.html',
  styleUrl: './team.css'
})
export class TeamComponent implements OnInit, OnDestroy {

    private teamSubscription!: Subscription;
    teams!: TeamML[];

    #teamService: TeamService = inject(TeamService);

    ngOnInit(): void {
        this.initData();
        this.initTeamCreatedSubscription();
    }

    ngOnDestroy(): void {
        this.teamSubscription.unsubscribe();
    }

    initData(): void {
        this.teamSubscription = this.#teamService.getAllTeams().subscribe(
            response => this.teams = response
        );
    }

    initTeamCreatedSubscription(): void {
        this.teamSubscription = this.#teamService.teamCreated$.subscribe(
            (response: TeamCreatedSupplier) => {
                this.teams.push(response.team);
            });
    }
}
