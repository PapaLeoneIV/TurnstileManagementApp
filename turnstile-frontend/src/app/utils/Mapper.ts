
import { UserDTO} from '../core/dto/user-dto';

export function mapUserDTO(user: UserDTO): UserDTO {
  return {
    usertype: user.usertype,
    name: user.name,
    surname: user.surname,
    email: user.email,
    role: {
      description: user.role.description,
      level: user.role.level
    },
    company: {
      name: user.company.name,
      address: user.company.address
    },
    badge: {
      allowed_enter_time: user.badge.allowed_enter_time,
      allowed_exit_time: user.badge.allowed_exit_time,
      expiry: user.badge.expiry,
      rfid: user.badge.rfid
    }
  };
}
