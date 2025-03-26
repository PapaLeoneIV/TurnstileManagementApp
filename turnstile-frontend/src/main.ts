 import { provideKeycloak } from 'keycloak-angular';
 import { bootstrapApplication } from '@angular/platform-browser';
 import { AppComponent } from './app/app.component';
 import {provideRouter} from '@angular/router';
 import {routes} from './app/app.routes';
 import {provideHttpClient} from '@angular/common/http';
 import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';


 

 bootstrapApplication(AppComponent, {
   providers: [
     provideKeycloak({
       config: {
         url: 'http://localhost:8081/',
         realm: 'myrealm',
         clientId: 'myclient',
       },
       initOptions: {
         onLoad: 'login-required',
         checkLoginIframe: false,
         enableLogging: true,
         pkceMethod: 'S256'
       },
     }),
     provideRouter(routes), provideHttpClient(), provideAnimationsAsync()
   ],
 })