import {RoleDTO} from './role-dto';
import {CompanyDTO} from './company-dto';
import {BadgeDTO} from './badge-dto';

export class UserDTO {
    usertype = "";
    name= "";
    surname= "";
    email= "";
    role = new RoleDTO();
    company = new CompanyDTO();
    badge = new BadgeDTO();

    constructor(init?: Partial<UserDTO>) {
        Object.assign(this, init);
      }
}
