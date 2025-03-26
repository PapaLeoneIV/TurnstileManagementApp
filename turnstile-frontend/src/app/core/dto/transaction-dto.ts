import {UserDTO} from './user-dto';
import {TurnstileDTO} from './turnstile-dto';

export class TransactionDTO {
    id: number;
   date: string;
   time: string;
   current_state : string;
   user: UserDTO;
   turnstile: TurnstileDTO;

   constructor(id: number, date: string, time: string, current_state: string,  user: UserDTO, turnstile: TurnstileDTO) {
     this.id = id;
     this.date = date;
     this.time = time;
     this.current_state = current_state;
     this.user = user;
     this.turnstile = turnstile;

   }
}
