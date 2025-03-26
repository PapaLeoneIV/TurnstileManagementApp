

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiResponse } from '@shared/models/api-response';
import { Observable } from 'rxjs';
import { RoleDTO } from '@core/dto/role-dto';
import { catchError, map } from 'rxjs/operators';
import { of } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class RoleService {
    private baseUrl = "http://localhost:8080/";
    constructor(private http: HttpClient) { }

  getRoleList(): Observable<string[]> {
    const apiUrl = this.baseUrl + 'role/all';

    return this.http.get<ApiResponse<RoleDTO>>(apiUrl).pipe(
      map(response => {
        if (!response.data) {
          return [];
        }

        return response.data.map(role => role.description)
      }),
      catchError(error => {
        console.error("Error fetching company list", error);
        return of([]); 
      })
    );
  }

}