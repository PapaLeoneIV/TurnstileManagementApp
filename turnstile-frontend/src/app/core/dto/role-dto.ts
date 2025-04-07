export  class RoleDTO {
    description = "";
    level = 0;

    constructor(init?: Partial<RoleDTO>) {
        Object.assign(this, init);
      }
}
