import {providePrimeNG} from 'primeng/config';
import Aura from '@primeuix/themes/aura';
import {ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection} from '@angular/core';
import {routes} from './app.routes';
import {provideRouter} from '@angular/router';
import {provideHttpClient, withFetch} from '@angular/common/http';
import {provideAnimations} from '@angular/platform-browser/animations';
import {MessageService} from 'primeng/api';

export const appConfig: ApplicationConfig = {
    providers: [
        provideHttpClient(withFetch()),
        provideRouter(routes),
        provideBrowserGlobalErrorListeners(),
        provideZoneChangeDetection({ eventCoalescing: true }),
        provideAnimations(),
        MessageService,
        providePrimeNG({
            theme: {
                preset: Aura
            }
        })
    ]
};
