import {Component, inject, OnDestroy, OnInit} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {Subscription} from 'rxjs';
import {TeamML} from '../../models/TeamML';
import {TeamCreatedSupplier, TeamService} from '../../services/team/team';
import {DxiColumnComponent, DxoPagerComponent, DxoPagingComponent} from 'devextreme-angular/ui/nested';
import {DxDataGridComponent, DxDataGridModule} from 'devextreme-angular';
import {RowClickEvent} from 'devextreme/ui/data_grid';

@Component({
  selector: 'app-team',
    imports: [
        RouterLink,
        DxiColumnComponent,
        DxoPagingComponent,
        DxDataGridComponent,
        DxoPagerComponent,
        DxDataGridComponent,
        DxDataGridModule
    ],
  templateUrl: './team.html',
  styleUrl: './team.css'
})
export class TeamComponent implements OnInit, OnDestroy {

    private teamSubscription!: Subscription;
    teams!: TeamML[];

    #teamService: TeamService = inject(TeamService);
    #router: Router = inject(Router);

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

    showDetails(event: RowClickEvent) {
        const id = event.data.id;
        this.#router.navigate(['/persons/', id]);
    }
}
