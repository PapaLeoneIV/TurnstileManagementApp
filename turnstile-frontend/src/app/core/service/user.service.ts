import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {UserDTO} from '@core/dto/user-dto';
import {ApiResponse} from '@shared/models/api-response';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private  baseUrl = "http://localhost:8080/";
  constructor(private http: HttpClient) {}

  getUsersWithCompanyName(companyName: string): Observable<ApiResponse<UserDTO>> {
    const apiUrl = this.baseUrl + 'info/users/companyName'
    return this.http.post<any>(apiUrl, {companyName: companyName})
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

  getUserInsideOffice(userType: string): Observable<ApiResponse<UserDTO>> {
    const body = {
      "userType": userType
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

}
