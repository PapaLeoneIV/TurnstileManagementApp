package it.tdgc.turnstile.service;

import it.tdgc.turnstile.dto.*;
import it.tdgc.turnstile.model.Badge;
import it.tdgc.turnstile.model.Company;
import it.tdgc.turnstile.model.Role;
import it.tdgc.turnstile.model.Users;
import it.tdgc.turnstile.repository.BadgeRepository;
import it.tdgc.turnstile.repository.CompanyRepository;
import it.tdgc.turnstile.repository.RoleRepository;
import it.tdgc.turnstile.repository.UsersRepository;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
import org.apache.catalina.User;
import org.apache.coyote.Response;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    final private UsersRepository usersRepository;
    private final MapperInterface mapperInterface;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final BadgeRepository badgeRepository;

    @Autowired
    public UserService(UsersRepository usersRepository, MapperInterface mapperInterface, RoleRepository roleRepository, CompanyRepository companyRepository, BadgeRepository badgeRepository) {
        this.usersRepository = usersRepository;
        this.mapperInterface = mapperInterface;
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
        this.badgeRepository = badgeRepository;
    }


    @Transactional
    public List<UserDTO> getUserByCompanyName(String companyName){
        List<Users> usersList = usersRepository.getUsersByCompanyName(companyName);
        if(usersList == null || usersList.isEmpty()){
            return null;
        }
        return usersList.stream().map(mapperInterface::toUserDTO).toList();
    }

    @Transactional
    public List<UserDTO>getAllUsers(){
        List<Users> users = usersRepository.findAll();
        if(users.isEmpty()) {
            return null;
        }

        return users.stream().map(mapperInterface::toUserDTO).toList();
    }

    @Transactional
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllEmployees(){
        List<Users> employees = usersRepository.findAllEmployees();
        if(employees.isEmpty()) {
            return buildResponse(HttpStatus.OK, "No employees found", null);
        }

        List<UserDTO> userDTOs = employees.stream().map(mapperInterface::toUserDTO).toList();
        return buildResponse(HttpStatus.OK, "OK", userDTOs);
    }


    @Transactional
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllVisitors(){
        List<Users> visitors = usersRepository.findAllVisitors();
        if(visitors.isEmpty()) {
            return buildResponse(HttpStatus.OK, "No visitors found", null);
        }

        List<UserDTO> userDTOs = visitors.stream().map(mapperInterface::toUserDTO).toList();
        return buildResponse(HttpStatus.OK, "OK", userDTOs);
    }

    @Transactional
    public  ResponseEntity<ApiResponse<Users>> insert(UserInsertDTO u){
        Users newUser = new Users();

        newUser.setUsertype(u.getUsertype());
        newUser.setName(u.getName());

        if(usersRepository.existsUsersByEmail(u.getEmail()))
            return buildResponse(HttpStatus.OK, "User email  already registered", null);

        newUser.setEmail(u.getEmail());
        newUser.setSurname(u.getSurname());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalTime allowedEnterTime = LocalTime.parse(u.getAllowed_enter_time(), timeFormatter);
        LocalTime allowedExitTime = LocalTime.parse(u.getAllowed_exit_time(), timeFormatter);
        LocalDate expiryDate = LocalDate.parse(u.getExpiry(), dateFormatter);

        Optional<Role> role = roleRepository.findById(u.getRole_id());
        if(role.isEmpty()){
            return buildResponse(HttpStatus.OK, "Role not found", null);
        }
        newUser.setRole(role.get());

        Optional<Company> company = companyRepository.findById(u.getCompany_id());
        if(company.isEmpty()){
            return buildResponse(HttpStatus.OK, "Company not found", null);
        }
        newUser.setCompany(company.get());

        String newRfid = UUID.randomUUID().toString();

        Badge newBadge = badgeRepository.save(new Badge(
            null,
                allowedEnterTime,
                allowedExitTime,
                expiryDate,
                newRfid
        ));


        newUser.setBadge(newBadge);
        Users users = usersRepository.save(newUser);

        return buildResponse(HttpStatus.OK, "OK", users);

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
