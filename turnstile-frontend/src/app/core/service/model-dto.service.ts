import { Injectable, EventEmitter  } from "@angular/core";
import { UserInsertDTO } from "../dto/add/user-insert-dto";
import { CompanyDTO } from "../dto/company-dto";
import { BadgeDTO } from "../dto/badge-dto";
import { TransactionEvent } from "../dto/trasaction-event.dto";
import { ErrorLog } from "../dto/error-log.dto";
import { InsideOfficeInsertDTO } from "../dto/add/inside-office-insert.dto";
import { TransactionInsertDTO } from "../dto/add/transaction-insert.dto";
import { RoleInsertDTO } from "../dto/add/role-insert-dto";
import { TurnstileInsertDTO } from "../dto/add/turnstile-insert.dto";
import { UserDeleteDTO } from "../dto/delete/user-delete.dto";
import { TransactionDeleteDTO } from "../dto/delete/transaction-delete.dto";
import { TurnstileDeleteDTO } from "../dto/delete/turnstile-delete.dto";
import { InsideOfficeDeleteDTO } from "../dto/delete/inside-office-delete.dto";
import { RoleDeleteDTO } from "../dto/delete/role-delete.dto";
import { UserDTO } from "../dto/user-dto";
import { TransactionDTO } from "../dto/transaction-dto";
import { TurnstileDTO } from "../dto/turnstile-dto";
import { InsideOffice } from "../dto/inside-office.dto";
import { RoleDTO } from "../dto/role-dto";

@Injectable({
    providedIn: "root"
})
export class ModelDTOService {
    ee: EventEmitter<any> = new EventEmitter<any>();

    getRegularDTO(dtoName: string) {
        switch (dtoName) {
            case "ROLE":
                return new RoleDTO();
            case "USER":
                return new UserDTO();
            case "COMPANY":
                return new CompanyDTO();
            case "BADGE":
                return new BadgeDTO();
            case "TRANSACTIONS":
                return new TransactionDTO();
            case "TURNSTILE":
                return new TurnstileDTO();
            case "INSIDE_OFFICE":
                return new InsideOffice();
            case "TRANSACTION_EVENT":
                return new TransactionEvent();
            case "ERROR_LOG":
                return new ErrorLog();
             // TODO : add more cases here
            default:
                return new UserDTO();
            }
    }
    getInsertDTO(dtoName: string) {
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
    getDeleteDTO(dtoName: string) {
        switch (dtoName) {
            case "ROLE":
                return new RoleDeleteDTO();
            case "USER":
                return new UserDeleteDTO();
            case "COMPANY":
                return new CompanyDTO();
            case "BADGE":
                return new BadgeDTO();
            case "TRANSACTIONS":
                return new TransactionDeleteDTO();
            case "TURNSTILE":
                return new TurnstileDeleteDTO();
            case "INSIDE_OFFICE":
                return new InsideOfficeDeleteDTO();
            case "TRANSACTION_EVENT":
                return new TransactionEvent();
            case "ERROR_LOG":
                return new ErrorLog();
             // TODO : add more cases here
            default:
                return new UserDeleteDTO();
            }
    }
}