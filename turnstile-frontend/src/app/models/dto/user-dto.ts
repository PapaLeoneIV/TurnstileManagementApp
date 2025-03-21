import {RoleDTO} from './role-dto';
import {CompanyDTO} from './company-dto';
import {BadgeDTO} from './badge-dto';

export class UserDTO {
    usertype : string;
    name: string;
    surname: string;
    email: string;
    role: RoleDTO;
    company: CompanyDTO;
    badge: BadgeDTO;

    constructor(usertype: string, name: string, surname: string, email: string,role : RoleDTO, company: CompanyDTO, badge: BadgeDTO) {
        this.usertype = usertype;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.company = company;
        this.badge = badge;
        }
}
