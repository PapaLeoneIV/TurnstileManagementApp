import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TransactionDTO } from '@core/dto/transaction-dto';
import { ApiResponse } from '@shared/models/api-response';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private  baseUrl = "http://localhost:8080/";
  constructor(private http: HttpClient) {}

  getLastTransaction(): Observable<ApiResponse<TransactionDTO>>{
    const apiUrl = this.baseUrl + 'info/transaction/last'
    return this.http.get<any>(apiUrl);
  }
}