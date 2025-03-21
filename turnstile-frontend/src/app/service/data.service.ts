import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, map, Observable, of} from 'rxjs';
import {UserDTO} from '../models/dto/user-dto';
import {ApiResponse} from '../models/api-response';
import {EnterInfoDTO} from '../models/dto/enter-info.dto';
import {TransactionDTO} from '../models/dto/transaction-dto';
import {CompanyDTO} from '../models/dto/company-dto';
import {RoleDTO} from '../models/dto/role-dto';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private  baseUrl = "http://localhost:8080/";
  constructor(private http: HttpClient) {}

  getUsersWithCompanyName(companyName: string): Observable<ApiResponse<UserDTO>> {
    const apiUrl = this.baseUrl + 'info/users/companyName'
    return this.http.post<any>(apiUrl, {companyName: companyName})
  }

  getLastTransaction(): Observable<ApiResponse<TransactionDTO>>{
    const apiUrl = this.baseUrl + 'info/transaction/last'
    return this.http.get<any>(apiUrl);
  }

  getByEmployee(): Observable<ApiResponse<UserDTO>> {
    const apiUrl = this.baseUrl + 'info/all_employees';
    return this.http.get<any>(apiUrl);
  }

  getByVisitor(): Observable<ApiResponse<UserDTO>> {
    const apiUrl = this.baseUrl + 'info/all_visitors';
    return this.http.get<any>(apiUrl);
  }

  getAllUsers(): Observable<ApiResponse<UserDTO>> {
    const apiUrl = this.baseUrl + 'info/all_users';
    return this.http.get<any>(apiUrl);
  }

  getUserInsideOffice(name: string): Observable<ApiResponse<UserDTO>> {
    const body = {
      "userType": name
    }
    const options = {
      headers: {
        "Content-Type": "application/json"
      }
    }
    const apiUrl = this.baseUrl + 'info/users/inside';
    return this.http.post<any>(apiUrl, body, options);
  }

  getUserByRole(name: string): Observable<ApiResponse<UserDTO>> {
    const body = {
      "description": name,
        "userType": "User"
    }
    const options = {
      headers: {
        "Content-Type": "application/json"
      }
    }
    const apiUrl = this.baseUrl + 'info/users/role';
    return this.http.post<any>(apiUrl, body, options);
  }

  getUserByCompany(): Observable<ApiResponse<UserDTO>> {
    const body = {
      "name": "TDG Consulting",
      "userType": "Employee"
    }
    const options = {
      headers: {
        "Content-Type": "application/json"
      }
    }
    const apiUrl = this.baseUrl + 'info/users/company';
    return this.http.post<any>(apiUrl, body, options);
  }

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
        return of([]);  // Return an empty array in case of an error
      })
    );
  }


  Enter(enterInfoDTO: EnterInfoDTO){
    const apiUrl = this.baseUrl + 'doorman/enter';
    return this.http.post<any>(apiUrl, enterInfoDTO);
  }

  Exit(enterInfoDTO: EnterInfoDTO){
    const apiUrl = this.baseUrl + 'doorman/exit';
    return this.http.post<any>(apiUrl, enterInfoDTO);
  }
}
