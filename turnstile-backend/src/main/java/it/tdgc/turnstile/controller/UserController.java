package it.tdgc.turnstile.controller;


import it.tdgc.turnstile.dto.UserDTO;
import it.tdgc.turnstile.dto.UserInsertDTO;
import it.tdgc.turnstile.model.Users;
import it.tdgc.turnstile.service.UserService;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
 @Transactional
 @RequestMapping(path = "/user")
 public class UserController {

     private final UserService userService;

     public UserController(UserService userService) {
         this.userService = userService;
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

// //
// //    @GetMapping("/company")
// //    public ResponseEntity<ApiResponse<List<UserDTO>>> groupUserByCompany(){
// //        return userService.groupUserByCompany();
// //    }
// //
// //
// //    @GetMapping("/id/{id}")
// //    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable("id") Integer id) {
// //        return userService.getUserById(id);
// //    }
// //
// //    @DeleteMapping("/delete/id/{id}")
// //    public ResponseEntity<ApiResponse<UserDTO>> deleteUserById(@PathVariable Integer id) {
// //        return userService.deleteUserById(id);
// //    }
// //
// //    @PutMapping("/update")
// //    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@RequestBody Users user) {
// //        return userService.updateUser(user);
// //    }
// //
    @PostMapping("/insert")
    public ResponseEntity<ApiResponse<Users>> insertUser(@RequestBody UserInsertDTO userInsertDTO) {
        return userService.insert(userInsertDTO);
    }
 }
