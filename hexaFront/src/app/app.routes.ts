import {Routes} from '@angular/router';
import {PersonComponent} from './components/person/person';
import {PersonDetailsComponent} from './components/person/person-details/person-details';
import {PersonNewComponent} from './components/person/person-new/person-new';
import {HomeComponent} from './components/home/home';
import {TeamComponent} from './components/team/team';
import {TeamDetailsComponent} from './components/team/team-details/team-details';
import {TeamNewComponent} from './components/team/team-new/team-new';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent
    },
    {
        path: 'persons',
        component: PersonComponent,
        title: 'Person List'
    },
    {
        path: 'persons/new',
        component: PersonNewComponent,
        title: 'New Person'
    },
    {
        path: 'persons/:id',
        component: PersonDetailsComponent,
        title: 'Person Details'
    },
    {
        path: 'teams',
        component: TeamComponent,
        title: 'Teams List'
    },
    {
        path: 'teams/new',
        component: TeamNewComponent,
        title: 'New Team'
    },
    {
        path: 'teams/:id',
        component: TeamDetailsComponent,
        title: 'Teams Details'
    },
];
