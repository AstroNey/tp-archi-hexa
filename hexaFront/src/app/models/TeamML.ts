import {PersonML} from './PersonML';

export class TeamML {
    id: number = -1; // default value for new object
    name!: string;
    persons: PersonML[] = [];
}
