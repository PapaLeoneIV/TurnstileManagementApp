package it.tdgc.turnstile.service;

import it.tdgc.turnstile.dto.UserDTO;
import it.tdgc.turnstile.model.Users;
import it.tdgc.turnstile.repository.UsersRepository;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    final private UsersRepository usersRepository;
    private final MapperInterface mapperInterface;

    @Autowired
    public UserService(UsersRepository usersRepository, MapperInterface mapperInterface) {
        this.usersRepository = usersRepository;
        this.mapperInterface = mapperInterface;
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
