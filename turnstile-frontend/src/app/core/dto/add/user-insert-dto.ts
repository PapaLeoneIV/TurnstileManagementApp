export class UserInsertDTO {
    usertype = "";
    name= "";
    surname= "";
    email= "";
    role_id = "";
    company_id = "";
    allowed_enter_time = "";
    allowed_exit_time = "";
    expiry = "";

    constructor(init?: Partial<UserInsertDTO>) {
        Object.assign(this, init);
      }
}
