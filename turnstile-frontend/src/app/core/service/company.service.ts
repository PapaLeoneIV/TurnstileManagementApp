import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiResponse } from '@shared/models/api-response';
import { Observable } from 'rxjs';

import { CompanyDTO } from '@core/dto/company-dto';
import { catchError, map } from 'rxjs/operators';
import { of } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class CompanyService {
    private baseUrl = "http://localhost:8080/";
    constructor(private http: HttpClient) { }

    getCompanyList(): Observable<string[]> {
        const apiUrl = this.baseUrl + 'company/all';

        return this.http.get<ApiResponse<CompanyDTO>>(apiUrl).pipe(
            map(response => {
                if (!response.data) {
                    return [];
                }

                return response.data.map(company => company.name)
            }),
            catchError(error => {
                console.error("Error fetching company list", error);
                return of([]);
            })
        );
    }
}