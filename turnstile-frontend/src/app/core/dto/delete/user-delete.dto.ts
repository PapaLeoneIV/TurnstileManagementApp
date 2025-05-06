export class UserDeleteDTO {
  id = null;

    constructor(init?: Partial<UserDeleteDTO>) {
        Object.assign(this, init);
      }
}
