import { Component, signal } from '@angular/core';
import {RouterModule} from '@angular/router';
import {HttpClient} from '@angular/common/http';

@Component({
    selector: 'app-root',
    imports: [RouterModule],
    templateUrl: `./app.html`,
    styleUrl: './app.css'
})
export class App {
    constructor(private http: HttpClient) { }
}
