 import { bootstrapApplication } from '@angular/platform-browser';
 import { AppComponent } from './app/app.component';
import { appConfig } from '@app/app.config';
import { Provider, EnvironmentProviders } from '@angular/core';

export interface AppConfig{
     providers: Array<Provider | EnvironmentProviders>;
     apiBaseUrl: string;
     url: string ,
     realm: string ,
     client: string 

}
fetch('/keycloak/config.json')
  .then((response) => response.json())
  .then((config: AppConfig) => {
 bootstrapApplication(AppComponent, appConfig(config)).catch((err) => console.error(err));
})
