import { EventEmitter, Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
@Injectable({
  providedIn: 'root'
})
export class DeleteService{
constructor(private http: HttpClient) {}
    private  baseUrl = "http://localhost:8080/";


    delete_entry_ee: EventEmitter<any> = new EventEmitter<any>();

    deleteUser(id: number){
        const apiUrl = this.baseUrl + "user/delete/id/" + id;
        return this.http.delete<any>(apiUrl);
    }
    deleteRole(id: number){
        const apiUrl = this.baseUrl + "role/delete/id/" + id;
        return this.http.delete<any>(apiUrl);
    }
    deleteCompany(id: number){
        const apiUrl = this.baseUrl + "company/delete/id/" + id;
        return this.http.delete<any>(apiUrl);
    }
    deleteBadge(id: number){
        const apiUrl = this.baseUrl + "badge/delete/id/" + id;
        return this.http.delete<any>(apiUrl);
    }
    deleteTransaction(id: number){
        const apiUrl = this.baseUrl + "transaction/delete/id/" + id;
        return this.http.delete<any>(apiUrl);
    }
    deleteTurnstile(id: number){
        const apiUrl = this.baseUrl + "turnstile/delete/id/" + id;
        return this.http.delete<any>(apiUrl);
    }
    deleteInsideOffice(id: number){
        const apiUrl = this.baseUrl + "insideOffice/delete/id/" + id;
        return this.http.delete<any>(apiUrl);
    }
    deleteTransactionEvent(id: number){
        const apiUrl = this.baseUrl + "transaction_event/delete/id/" + id;
        return this.http.delete<any>(apiUrl);
    }
    deleteErrorLog(id: number){
        const apiUrl = this.baseUrl + "error_log/delete/id/" + id;
        return this.http.delete<any>(apiUrl);
    }
}