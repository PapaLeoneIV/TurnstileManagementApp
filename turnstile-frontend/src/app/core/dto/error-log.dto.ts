export class ErrorLog{
    id = null;
    created_at = null;
    error_message = null;
    user_id = null;
    turnstile_id = null;

    constructor(init?: Partial<ErrorLog>){
        Object.assign(this, init);
    }
}