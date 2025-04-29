import { Injectable, EventEmitter  } from "@angular/core";
import { UserInsertDTO } from "../dto/add/user-insert-dto";
import { CompanyDTO } from "../dto/company-dto";
import { RoleDTO } from "../dto/role-dto";
import { BadgeDTO } from "../dto/badge-dto";
import { TurnstileDTO } from "../dto/turnstile-dto";
import { TransactionEvent } from "../dto/trasaction-event.dto";
import { ErrorLog } from "../dto/error-log.dto";
import { InsideOfficeInsertDTO } from "../dto/add/inside-office-insert.dto";
import { TransactionInsertDTO } from "../dto/add/transaction-insert.dto";
import { RoleInsertDTO } from "../dto/add/role-insert-dto";
import { TurnstileInsertDTO } from "../dto/add/turnstile-insert.dto";
@Injectable({
    providedIn: "root"
})
export class ModelDTOService {
    ee: EventEmitter<any> = new EventEmitter<any>();


    getDTO(dtoName: string) {
        switch (dtoName) {
            case "ROLE":
                return new RoleInsertDTO();
            case "USER":
                return new UserInsertDTO();
            case "COMPANY":
                return new CompanyDTO();
            case "BADGE":
                return new BadgeDTO();
            case "TRANSACTIONS":
                return new TransactionInsertDTO();
            case "TURNSTILE":
                return new TurnstileInsertDTO();
            case "INSIDE_OFFICE":
                return new InsideOfficeInsertDTO();
            case "TRANSACTION_EVENT":
                return new TransactionEvent();
            case "ERROR_LOG":
                return new ErrorLog();
             // TODO : add more cases here
            default:
                return new UserInsertDTO();
            }
    }
}