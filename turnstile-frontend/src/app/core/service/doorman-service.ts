import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { EnterInfoDTO } from '@core/dto/enter-info.dto';
@Injectable({
  providedIn: 'root'
})
export class DoorManService {
  private  baseUrl = "http://localhost:8080/";
  constructor(private http: HttpClient) {}


  Enter(enterInfoDTO: EnterInfoDTO){
    const apiUrl = this.baseUrl + 'doorman/enter';
    return this.http.post<any>(apiUrl, enterInfoDTO);
  }

  Exit(enterInfoDTO: EnterInfoDTO){
    const apiUrl = this.baseUrl + 'doorman/exit';
    return this.http.post<any>(apiUrl, enterInfoDTO);
  }
}