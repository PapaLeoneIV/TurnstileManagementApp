package it.tdgc.turnstile.controller;


import it.tdgc.turnstile.dto.TurnstileDTO;
import it.tdgc.turnstile.dto.UserDTO;
import it.tdgc.turnstile.dto.UserInsertDTO;
import it.tdgc.turnstile.model.Users;
import it.tdgc.turnstile.service.UserService;
import it.tdgc.turnstile.util.ApiResponse;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
 @Transactional
 @RequestMapping(path = "/user")
 public class UserController {

     private final UserService userService;

     public UserController(UserService userService) {
         this.userService = userService;
     }

    @PostMapping("/insert")
    public ResponseEntity<ApiResponse<Users>> insertUser(@RequestBody @NotNull UserInsertDTO userInsertDTO) {
         return userService.insert(userInsertDTO);
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> searchUserById(@PathVariable("id") Integer id) {
         return userService.getUserById(id);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> deleteUserById(@PathVariable Integer id) {
        return userService.deleteUserById(id);
    }
 }
