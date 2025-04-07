import {UserDTO} from './user-dto';
import {TurnstileDTO} from './turnstile-dto';

export class TransactionDTO {
   id = 0;
   date = "";
   time = "";
   current_state  = "";
   user = new UserDTO();
   turnstile = new TurnstileDTO();

    constructor(init?: Partial<TransactionDTO>) {
      Object.assign(this, init);
    }
}
