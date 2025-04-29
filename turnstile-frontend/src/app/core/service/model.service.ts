import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";


@Injectable({
    providedIn: 'root'
    })
export class ModelService {
     private baseUrl = "http://localhost:8080/";
        constructor(private http: HttpClient) { }
    
    getModels() {
        const apiUrl = this.baseUrl + 'info/db/tables/name';
        return this.http.get<any>(apiUrl);
    }
}