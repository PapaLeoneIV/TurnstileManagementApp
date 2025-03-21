package it.tdgc.turnstile.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import it.tdgc.turnstile.dto.*;
import it.tdgc.turnstile.model.Users;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.tdgc.turnstile.service.InfoSearcherService;
import it.tdgc.turnstile.service.TransactionService;
import it.tdgc.turnstile.service.UserService;
import it.tdgc.turnstile.util.ApiResponse;
import jakarta.transaction.Transactional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Transactional
@RequestMapping(path = "/info")
public class InfoSearcherController {
    final private InfoSearcherService infoSearchService;
    private final UserService userService;
    private final TransactionService transactionService;

    public InfoSearcherController(InfoSearcherService infoSearchService, UserService userService, TransactionService transactionService){
        this.infoSearchService = infoSearchService;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @PostMapping("users/companyName")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getUserByCompanyName(@RequestBody CompanyNameDTO companyNameDTO){
        String companyName = companyNameDTO.getCompanyName();
        if(companyName == null){
            return buildResponse(HttpStatus.OK, "No company name provided", null);
        }
        List<UserDTO> users = userService.getUserByCompanyName(companyName);
        if(users == null || users.isEmpty()){
            return buildResponse(HttpStatus.OK, "No such user under company name: " + companyName, null);
        }
        return buildResponse(HttpStatus.OK, "OK", users);
    }


    @GetMapping("transaction/last")
    public ResponseEntity<ApiResponse<TransactionDTO>> getLastTransaction(){
        TransactionDTO tdto = transactionService.getLastTransaction();
        if(tdto == null){
            return buildResponse(HttpStatus.OK, "No transaction found", null);

        }
        return buildResponse(HttpStatus.OK, "OK", tdto);
    }

    @GetMapping("all_users")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getUsersInfo(){
        List<UserDTO> users = userService.getAllUsers();
        if(users == null || users.isEmpty()) {
            return buildResponse(HttpStatus.OK, "No users found", null);
        }
        return buildResponse(HttpStatus.OK, "OK", users);
    }

    @GetMapping("all_employees")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllEmployees() {
        return userService.getAllEmployees();

    }

    @GetMapping("all_visitors")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllVisitors() {
        return userService.getAllVisitors();
    }

    @PostMapping("/users/inside")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getUsersInsideOffice(@RequestBody IOSearchDTO ioSearchDTO){
        String userType = ioSearchDTO.getUserType();
        if (!userType.equals("User") && !userType.equals("Visitor") && !userType.equals("Employee")) {
            return buildResponse(HttpStatus.BAD_REQUEST, "User type is not valid: " + userType, null);
        }
        if(userType.equals("User")){
            List<UserDTO> userDTOS =  infoSearchService.getAllUsersInsideOffice();
            if(userDTOS.isEmpty())
                return buildResponse(HttpStatus.OK, "No users found", null);
            return buildResponse(HttpStatus.OK, "OK", userDTOS);
        }
        List<UserDTO> userDTOS =  infoSearchService.getPeopleInsideOffice(userType);
        if(userDTOS == null || userDTOS.isEmpty())
            return buildResponse(HttpStatus.OK, "No " + userType + " inside office",  null);
        return buildResponse(HttpStatus.OK, "OK", userDTOS);
    }

    @PostMapping("/users/role")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getUsersByRole(@RequestBody RoleSearchDTO roleSearchDTO){
        String role = roleSearchDTO.getDescription();
        if(role == null || role.isBlank())
            return buildResponse(HttpStatus.BAD_REQUEST, "Role is null", null);
        String userType = roleSearchDTO.getUserType();
        if(userType == null || userType.isBlank())
            return buildResponse(HttpStatus.BAD_REQUEST, "User type is null", null);
        List<UserDTO> userDTOList = infoSearchService.getByRole(role, userType);
        if(userDTOList == null)
            return buildResponse(HttpStatus.OK, "No users with role " + role, null);
        return buildResponse(HttpStatus.OK, "OK", userDTOList);
    }

    @PostMapping("/users/company")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getUsersByRole(@RequestBody CompanySearchDTO companyDTO){
        String name = companyDTO.getName();
        if(name == null || name.isBlank())
            return buildResponse(HttpStatus.BAD_REQUEST, "Role is null", null);
        String userType = companyDTO.getUserType();
        if(userType == null || userType.isBlank())
            return buildResponse(HttpStatus.BAD_REQUEST, "User type is null", null);
        List<UserDTO> userDTOList = infoSearchService.getByCompany(name, userType);
        if(userDTOList == null)
            return buildResponse(HttpStatus.OK, "No " + userType + " associated with company: " + name, null);
        return buildResponse(HttpStatus.OK, "OK", userDTOList);
    }


    @PostMapping("transactions/range-days")
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getEntranceOfTheDay(@RequestBody TimeDayFilterDTO timeDayFilterDTO){
        LocalDate start = timeDayFilterDTO.getStartDate();
        LocalDate end = timeDayFilterDTO.getEndDate();
        if(start == null || end == null){
            return buildResponse(HttpStatus.BAD_REQUEST, "Start or end date is null", null);
        }
        if(start.equals(end)){
            List<TransactionDTO> transactionDTOs = transactionService.getTransactionWithDate(start);
            if(transactionDTOs.isEmpty())
                return buildResponse(HttpStatus.OK, "No transactions found", null);
            return buildResponse(HttpStatus.OK, "OK", transactionDTOs);
        }
        if(start.isAfter(end)){
            return buildResponse(HttpStatus.BAD_REQUEST, "Start date is after end date", null);
        }
        List<TransactionDTO> transactionDTOS = transactionService.getTransactionWithDateRange(start, end);
        if(transactionDTOS == null || transactionDTOS.isEmpty())
            return buildResponse(HttpStatus.OK, "No transactions found", null);
        return buildResponse(HttpStatus.OK, "OK", transactionDTOS);
    }


    @PostMapping("transactions/range-hours")
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getEntranceOfTheHour(@RequestBody TimeHoursFilterDTO timeHoursFilterDTO){
        LocalDate day = timeHoursFilterDTO.getDay();
        LocalTime startHour = timeHoursFilterDTO.getStart();
        LocalTime endHour = timeHoursFilterDTO.getEnd();
        if(day == null || startHour == null || endHour == null){
            return buildResponse(HttpStatus.BAD_REQUEST, "Day or startHour or endHour date is null", null);
        }
        if(startHour.equals(endHour)){
            List<TransactionDTO> transactionDTOs = transactionService.getTransactionWithinHour(day, startHour);
            if(transactionDTOs.isEmpty())
                return buildResponse(HttpStatus.OK, "No transactions found", null);
            return buildResponse(HttpStatus.OK, "OK", transactionDTOs);
        }
        if(startHour.isAfter(endHour)){
            return buildResponse(HttpStatus.BAD_REQUEST, "Day or startHour date is after endHour date", null);
        }
        List<TransactionDTO> transactionDTOS = transactionService.getTransactionWithHoursRange(day, startHour, endHour);
        if(transactionDTOS == null || transactionDTOS.isEmpty())
            return buildResponse(HttpStatus.OK, "No transactions found", null);
        return buildResponse(HttpStatus.OK, "OK", transactionDTOS);
    }


    @PostMapping("transactions/byRole")
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getEntranceByRole(@RequestBody TransactionRoleFilterDTO transactionRoleFilterDTO){
        LocalDate day = transactionRoleFilterDTO.getDay();
        String role = transactionRoleFilterDTO.getRole();
        if(day == null || role == null)
            return buildResponse(HttpStatus.BAD_REQUEST, "Day or role is null", null);
        List<TransactionDTO> transactionDTOS = transactionService.getTransactionWithRole(day, role);
        if(transactionDTOS == null ||transactionDTOS.isEmpty())
            return buildResponse(HttpStatus.OK, "No transactions found", null);

        return buildResponse(HttpStatus.OK, "OK", transactionDTOS);
    }

    @PostMapping("transactions/byCompany")
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getEntranceByCompany(@RequestBody TransactionCompanyFilterDTO transactionCompanyFilterDTO){
        LocalDate day = transactionCompanyFilterDTO.getDay();
        if(day == null)
            return buildResponse(HttpStatus.BAD_REQUEST, "Day is null", null);
        String companyName = transactionCompanyFilterDTO.getCompanyName();
        if(companyName == null || companyName.isEmpty())
            return buildResponse(HttpStatus.BAD_REQUEST, "Company name is null", null);
        List<TransactionDTO> transactionDTOS = transactionService.getTransactionWithCompanyName(day, companyName);
        if(transactionDTOS == null || transactionDTOS.isEmpty())
            return buildResponse(HttpStatus.OK, "No transactions found", null);

        return buildResponse(HttpStatus.OK, "OK", transactionDTOS);
    }

    @PostMapping("transactions/byUser")
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getTransactionsByUser(@RequestBody UserIdDTO userIdDTO)
    {
        Integer userId = userIdDTO.getUserId();
        LocalDate day = userIdDTO.getDay();
        if(day == null)
            return buildResponse(HttpStatus.BAD_REQUEST, "Day is null", null);
        List<TransactionDTO> transactionDTOS = transactionService.getTransactionWithUserId(day, userId);
        if(transactionDTOS == null || transactionDTOS.isEmpty())
            return buildResponse(HttpStatus.OK, "No transactions found", null);

        return buildResponse(HttpStatus.OK, "OK", transactionDTOS);
    }


    private <T> ResponseEntity<ApiResponse<T>> buildResponse(HttpStatus status, String message, T data) {
        ApiResponse<T> response = new ApiResponse<>(
                String.valueOf(status.value()),
                message,
                data,
                new Date(),
                null
        );

        return ResponseEntity.status(status).body(response);
    }
}
