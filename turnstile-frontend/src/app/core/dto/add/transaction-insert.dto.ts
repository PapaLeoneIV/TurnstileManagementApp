export class TransactionInsertDTO{
        date = null;
        time = null;
        current_state  = null;
        user_id = null;
        turnstile_id = null;
    
        constructor(init?: Partial<TransactionInsertDTO>) {
          Object.assign(this, init);
        }
}