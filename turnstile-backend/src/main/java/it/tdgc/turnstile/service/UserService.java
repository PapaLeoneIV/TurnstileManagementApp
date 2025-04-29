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
import it.tdgc.turnstile.util.responseBuilder;
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
            return responseBuilder.buildResponse(HttpStatus.OK, "No employees found", null);
        }

        List<UserDTO> userDTOs = employees.stream().map(mapperInterface::toUserDTO).toList();
        return responseBuilder.buildResponse(HttpStatus.OK, "OK", userDTOs);
    }


    @Transactional
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllVisitors(){
        List<Users> visitors = usersRepository.findAllVisitors();
        if(visitors.isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.OK, "No visitors found", null);
        }

        List<UserDTO> userDTOs = visitors.stream().map(mapperInterface::toUserDTO).toList();
        return responseBuilder.buildResponse(HttpStatus.OK, "OK", userDTOs);
    }

    @Transactional
    public ResponseEntity<ApiResponse<Users>> insert(UserInsertDTO u) {
        if(!u.getUsertype().equals("Employee") &&  !u.getUsertype().equals("Visitor") ) {
            return responseBuilder.buildResponse(HttpStatus.CONFLICT, "User type is not valid!", null);

        }
        if (usersRepository.existsUsersByEmail(u.getEmail())) {
            return responseBuilder.buildResponse(HttpStatus.CONFLICT, "User email already registered", null);
        }

        Optional<Role> roleOpt = roleRepository.findById(u.getRole_id());
        if (roleOpt.isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.CONFLICT, "Role not found", null);
        }

        Optional<Company> companyOpt = companyRepository.findById(u.getCompany_id());
        if (companyOpt.isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.CONFLICT, "Company not found", null);
        }

        Users newUser = new Users();
        newUser.setUsertype(u.getUsertype());
        newUser.setName(u.getName());
        newUser.setEmail(u.getEmail());
        newUser.setSurname(u.getSurname());
        newUser.setRole(roleOpt.get());
        newUser.setCompany(companyOpt.get());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalTime allowedEnterTime = LocalTime.parse(u.getAllowed_enter_time(), timeFormatter);
        LocalTime allowedExitTime = LocalTime.parse(u.getAllowed_exit_time(), timeFormatter);
        LocalDate expiryDate = LocalDate.parse(u.getExpiry(), dateFormatter);

        Badge newBadge = badgeRepository.save(new Badge(
                null,
                allowedEnterTime,
                allowedExitTime,
                expiryDate,
                UUID.randomUUID().toString()
        ));

        newUser.setBadge(newBadge);

        Users savedUser = usersRepository.save(newUser);

        return responseBuilder.buildResponse(HttpStatus.OK, "OK", savedUser);
    }


}
