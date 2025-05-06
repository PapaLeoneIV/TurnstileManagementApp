export class RoleDeleteDTO{
    level =  null;
    constructor(init?: Partial<RoleDeleteDTO>) {
        Object.assign(this, init);
      }
}