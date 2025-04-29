export class TransactionEvent{
    state = null;
    created_at = null;
    transaction_id = null;

    constructor(init? : Partial<TransactionEvent>){
        Object.assign(this, init);
    }
}