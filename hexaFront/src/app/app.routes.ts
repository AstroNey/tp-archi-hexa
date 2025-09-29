import { Routes } from '@angular/router';
import {PersonComponent} from './components/person/person';
import {PersonDetailsComponent} from './components/person/person-details/person-details';
import {PersonNewComponent} from './components/person/person-new/person-new';
import {HomeComponent} from './components/home/home';

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
];
