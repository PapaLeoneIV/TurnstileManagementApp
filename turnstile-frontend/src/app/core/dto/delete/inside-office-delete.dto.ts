export class InsideOfficeDeleteDTO{
    user_id = null;

    constructor(init? : Partial<InsideOfficeDeleteDTO>){
        Object.assign(this, init);
    }
}