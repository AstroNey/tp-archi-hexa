import {inject, Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {PersonException} from '../../enum/PersonException';
import {TeamException} from '../../enum/TeamException';
import {MessageService} from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

    #messageService = inject(MessageService);

    handleError(error: any): Observable<any> {
        if (error) {
            if (error.error.exceptions) {
                for (let ex of error.error.exceptions.reverse()) {
                    this.#messageService.add({
                        severity: 'error',
                        summary: 'Erreur',
                        life: 5000,
                        detail: this.getMessageFromError(ex.errorMessage),
                    });
                }
            }
        }

        throw of(error);
    }

    getMessageFromError(error: any): string {
        let msg;
        msg = PersonException[error as keyof typeof PersonException];
        if (!msg) {
            msg = TeamException[error as keyof typeof TeamException];
        }
        return msg;
    }
}
