import { Injectable, EventEmitter  } from "@angular/core";
import {UserDTO} from '../models/dto/user-dto';
import {ApiResponse} from '../models/api-response';
@Injectable({
    providedIn: "root"
})
export class ButtonFilterService {
    ee: EventEmitter<UserDTO[]> = new EventEmitter<UserDTO[]>();
}

@Injectable({
  providedIn: "root"
})
export class MainFilterService{
  ee: EventEmitter<string> = new EventEmitter<string>();
}
