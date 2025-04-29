export class RoleInsertDTO{
    level =  null;
    description = null;

    constructor(init?: Partial<RoleInsertDTO>) {
        Object.assign(this, init);
      }
}