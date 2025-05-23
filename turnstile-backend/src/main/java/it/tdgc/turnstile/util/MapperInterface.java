package it.tdgc.turnstile.util;

import it.tdgc.turnstile.dto.*;
import it.tdgc.turnstile.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MapperInterface {

    @Mapping(source="state", target="state")
    @Mapping(source="created_at", target="created_at")
    @Mapping(source="transaction", target="transaction")
    TransactionEventDTO toTransactionEventDTO(TransactionEvent transactionEvent);

    @Mapping(source="id", target="id")
    @Mapping(source="date", target="date")
    @Mapping(source="time", target="time")
    @Mapping(source="current_state", target="current_state")
    @Mapping(source="user", target="user")
    @Mapping(source="turnstile", target="turnstile")
    TransactionDTO  toTransactionDto(Transaction transaction);

    @Mapping(source="name", target="name")
    @Mapping(source="address", target="address")
    CompanyDTO toCompanyDTO(Company company);

    @Mapping(source="id", target="id")
    @Mapping(source="available", target="available")
    TurnstileDTO toTurnstileDTO(Turnstile turnstile);

    @Mapping(source="level", target="level")
    @Mapping(source="description", target="description")
    RoleDTO toRoleDTO(Role role);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "surname", target = "surname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "badge", target = "badge")
    @Mapping(source = "company", target = "company")
    @Mapping(source = "role", target = "role")
    UserDTO toUserDTO(Users user);

    @Mapping(source="rfid", target="rfid")
    @Mapping(source="allowed_enter_time", target="allowed_enter_time")
    @Mapping(source="allowed_exit_time", target="allowed_exit_time")
    @Mapping(source = "expiry", target = "expiry")
    BadgeDTO toBadgeDTO(Badge badge);


    @Mapping(source = "rfid", target = "rfid")
    @Mapping(source = "allowed_enter_time", target = "allowed_enter_time")
    @Mapping(source = "allowed_exit_time", target = "allowed_exit_time")
    @Mapping(source = "expiry", target = "expiry")
    Badge toBadge(BadgeDTO badgeDTO);

    @Mapping(source = "level", target = "level")
    @Mapping(source = "description", target = "description")
    Role toRole(RoleDTO roleDTO);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "address", target = "address")
    Company toCompany(CompanyDTO companyDTO);


    @Mapping(source="created_at", target="created_at")
    @Mapping(source="error_message", target="error_message")
    @Mapping(source="turnstile.id", target="turnstile_id")
    @Mapping(source="user.id", target="user_id")
    ErrorLogDTO toErrorLogDTO(ErrorLog errorLog);

    @Mapping(source="id", target="id")
    @Mapping(source="user", target="user")
    InsideOfficeDTO toInsideOfficeDTO(InsideOffice newIo);
}
