export class InsideOfficeInsertDTO{
    user_id = null;

    constructor(init? : Partial<InsideOfficeInsertDTO>){
        Object.assign(this, init);
    }
}