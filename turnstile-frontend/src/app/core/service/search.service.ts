import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
@Injectable({
  providedIn: 'root'
})
export class SearchService{
constructor(private http: HttpClient) {}
    private  baseUrl = "http://localhost:8080/";


    searchUserByID(id: number){
        const apiUrl = this.baseUrl + "user/search/id/" + id;
        return this.http.get<any>(apiUrl);
    }
    searchRoleByID(id: number){
        const apiUrl = this.baseUrl + "role/search/id/" + id;
        return this.http.get<any>(apiUrl);
    }
    searchCompanyByID(id: number){
        const apiUrl = this.baseUrl + "company/search/id/" + id;
        return this.http.get<any>(apiUrl);
    }
    searchBadgeByID(id: number){
        const apiUrl = this.baseUrl + "badge/search/id/" + id;
        return this.http.get<any>(apiUrl);
    }
    searchTransactionByID(id: number){
        const apiUrl = this.baseUrl + "transaction/search/id/" + id;
        return this.http.get<any>(apiUrl);
    }
    searchTurnstileByID(id: number){
        const apiUrl = this.baseUrl + "turnstile/search/id/" + id;
        return this.http.get<any>(apiUrl);
    }
    searchInsideOfficeByID(id: number){
        const apiUrl = this.baseUrl + "insideOffice/search/id/" + id;
        return this.http.get<any>(apiUrl);
    }
    searchTransactionEventByID(id: number){
        const apiUrl = this.baseUrl + "transaction_event/search/id/" + id;
        return this.http.get<any>(apiUrl);
    }
    searchErrorLogByID(id: number){
        const apiUrl = this.baseUrl + "error_log/search/id/" + id;
        return this.http.get<any>(apiUrl);
    }
}