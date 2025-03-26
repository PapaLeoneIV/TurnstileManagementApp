import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment as e } from '@enviroment/enviroment';

@Injectable({
  providedIn: 'root',
})
export class LogoutService {
  constructor(private http: HttpClient) {}

  performLogout() {
    const headers = {
        'Content-Type': 'application/x-www-form-urlencoded',
        Authorization: `Bearer ${localStorage.getItem('access_token')}`,
    }
    const url = `${e.Keycloak.baseUrl}/realms/${e.Keycloak.realmName}/protocol/openid-connect/logout`;
    return this.http.post(url, {});
  }
}