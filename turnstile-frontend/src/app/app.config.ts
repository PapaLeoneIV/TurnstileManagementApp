import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideHttpClient,
   withInterceptors } from '@angular/common/http';
import { includeBearerTokenInterceptor, provideKeycloak } from 'keycloak-angular';
import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { AppConfig } from 'main';
import { initializeKeycloak } from './utils/keycloak/config/config';

//Look into authInterceptor for TODO!!!
// import { authInterceptor } from './auth.interceptor';

export function appConfig (config : AppConfig) : ApplicationConfig {
   return  { providers: [
      provideRouter(routes),
      provideZoneChangeDetection({ eventCoalescing: true }),
      provideKeycloak(initializeKeycloak(config)),
      provideHttpClient( withInterceptors([includeBearerTokenInterceptor/* , authInterceptor */]) )],
  }
};


