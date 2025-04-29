import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { UserInsertDTO } from "../dto/add/user-insert-dto";
import { RoleInsertDTO } from "../dto/add/role-insert-dto";
import { CompanyDTO } from "../dto/company-dto";
import { RoleDTO } from "../dto/role-dto";
import { BadgeDTO } from "../dto/badge-dto";
import { TransactionDTO } from "../dto/transaction-dto";
import { TransactionInsertDTO } from "../dto/add/transaction-insert.dto";
import { TurnstileDTO } from "../dto/turnstile-dto";
import { InsideOffice } from "../dto/inside-office.dto";
import { InsideOfficeInsertDTO } from "../dto/add/inside-office-insert.dto";
import { TransactionEvent } from "../dto/trasaction-event.dto";
import { ErrorLog } from "../dto/error-log.dto";
import { TurnstileInsertDTO } from "../dto/add/turnstile-insert.dto";
@Injectable({
  providedIn: 'root'
})
export class InsertService{
constructor(private http: HttpClient) {}
    private  baseUrl = "http://localhost:8080/";


    addUser(insertUserDTO: UserInsertDTO){
        const apiUrl = this.baseUrl + "user/insert";
        return this.http.post<any>(apiUrl, insertUserDTO);
    }
    addRole(insertRoleDTO: RoleDTO){
        const apiUrl = this.baseUrl + "role/insert";
        return this.http.post<any>(apiUrl, insertRoleDTO);
    }
    addCompany(insertCompanyDTO: CompanyDTO){
        const apiUrl = this.baseUrl + "company/insert";
        return this.http.post<any>(apiUrl, insertCompanyDTO);
    }
    addBadge(insertBadgeDTO: BadgeDTO){
        const apiUrl = this.baseUrl + "badge/insert";
        return this.http.post<any>(apiUrl, insertBadgeDTO);
    }
    addTransaction(insertTransactionDTO: TransactionInsertDTO){
        const apiUrl = this.baseUrl + "transaction/insert";
        return this.http.post<any>(apiUrl, insertTransactionDTO);
    }
    addTurnstile(insertTurnstileDTO: TurnstileInsertDTO){
        const apiUrl = this.baseUrl + "turnstile/insert";
        return this.http.post<any>(apiUrl, insertTurnstileDTO);
    }
    addInsideOffice(insertInsideOfficeDTO: InsideOfficeInsertDTO){
        const apiUrl = this.baseUrl + "insideOffice/insert";
        return this.http.post<any>(apiUrl, insertInsideOfficeDTO);
    }
    addTransactionEvent(insertTransactionEventDTO: TransactionEvent){
        const apiUrl = this.baseUrl + "transaction_event/insert";
        return this.http.post<any>(apiUrl, insertTransactionEventDTO);
    }
    addErrorLog(insertErrorLogDTO: ErrorLog){
        const apiUrl = this.baseUrl + "error_log/insert";
        return this.http.post<any>(apiUrl, insertErrorLogDTO);
    }
}