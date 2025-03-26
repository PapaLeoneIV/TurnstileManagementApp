import { Injectable, EventEmitter  } from "@angular/core";
import {UserDTO} from '@core/dto/user-dto';
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
