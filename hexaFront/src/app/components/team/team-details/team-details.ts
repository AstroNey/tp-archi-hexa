import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {TeamML} from '../../../models/TeamML';
import {TeamService} from '../../../services/team/team';
import {AsyncPipe} from '@angular/common';

@Component({
  selector: 'app-team-details',
    imports: [
        AsyncPipe
    ],
  templateUrl: './team-details.html',
  styleUrl: './team-details.css'
})
export class TeamDetailsComponent implements OnInit {

    team$!: Observable<TeamML>;

    constructor(private route: ActivatedRoute,
                private teamService: TeamService) {
    }

    ngOnInit(): void {
        const id = this.route.snapshot.paramMap.get('id');
        if (id) {
            this.team$ = this.teamService.getTeamById(id);
            this.team$.subscribe(team => console.log('Fetched team:', team));
        }
    }
}
