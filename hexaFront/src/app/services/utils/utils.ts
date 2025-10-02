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
        let err = {msg: '', status: ''};
        if (error) {
            if (error.status === 400) {
                err = {
                    msg: this.getMessageFromError(error),
                    status: error.status
                };
            } else if (error.status === 404) {
                err = {
                    msg: this.getMessageFromError(error),
                    status: error.status
                };
            }

        } else if (error.status === 500) {
            err = {
                msg: this.getMessageFromError(error),
                status: error.status
            };
        }

        this.#messageService.add({
            severity: 'error',
            summary: 'Erreur' + (err.status ? ' ' + err.status : ''),
            life: 5000,
            detail: err.msg
        });

        throw of(error);
    }

    getMessageFromError(error: any): string {
        let msg = 'Une erreur est survenue';
        if (error) {
            if (error.error) {
                msg = PersonException[error.error as keyof typeof PersonException];
                if (!msg) {
                    msg = TeamException[error.error as keyof typeof TeamException];
                }
            }
        }

        return msg;
    }
}
