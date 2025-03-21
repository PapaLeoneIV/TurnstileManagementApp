package it.tdgc.turnstile.util;

import it.tdgc.turnstile.dto.*;
import it.tdgc.turnstile.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MapperInterface {

    MapperInterface INSTANCE = Mappers.getMapper(MapperInterface.class);

//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "user.name", target = "name")
//    @Mapping(source = "user.surname", target = "surname")
//    @Mapping(source = "user.email", target = "email")
//    @Mapping(source = "role", target = "role")
//    @Mapping(source = "company", target = "company")
//    @Mapping(source = "badge", target = "badge")
//    @Mapping(source = "permission", target = "permission")
//    EmployeeDTO toEmployeeDto(Employee employee);
//
//
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "user.name", target = "name")
//    @Mapping(source = "user.surname", target = "surname")
//    @Mapping(source = "user.email", target = "email")
//    @Mapping(source = "role", target = "role")
//    @Mapping(source = "company", target = "company")
//    @Mapping(source = "badge", target = "badge")
//    @Mapping(source = "permission", target = "permission")
//    VisitorDTO toVisitorDto(Visitor visitor);

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

//    @Mapping(source="numOfUsers", target="totUsersInsideOffice")
//    InsideOfficeDTO toInsideOfficeDTONumOfUsers(Integer numOfUsers);

//    @Mapping(source="allowed_enter_time", target="allowed_enter_time")
//    @Mapping(source="allowed_exit_time", target="allowed_exit_time")
//    @Mapping(source="end_of_permission", target="end_of_permission")
//    PermissionDTO toPermissionDTO(Permission permission);

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

    @Mapping(source="id", target="id")
    @Mapping(source="rfid", target="rfid")
    BadgeDTO toBadgeDTO(Badge badge);



}
