// package it.tdgc.turnstile.controller;

// import java.util.Date;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import it.tdgc.turnstile.service.UserService;
// import it.tdgc.turnstile.util.ApiResponse;
// import it.tdgc.turnstile.util.MapperInterface;
// import jakarta.transaction.Transactional;

// @RestController
// @Transactional
// @RequestMapping(path = "/user")
// public class UserController {

//     private final UserService userService;
//     private final MapperInterface mapperInterface;

//     public UserController(UserService userService, MapperInterface mapperInterface) {
//         this.userService = userService;
//         this.mapperInterface = mapperInterface;
//     }


    

//     // //TODO move into an interface
//     // private <T> ResponseEntity<ApiResponse<T>> buildResponse(HttpStatus status, String message, T data) {
//     //     ApiResponse<T> response = new ApiResponse<>(
//     //             String.valueOf(status.value()),
//     //             message,
//     //             data,
//     //             new Date(),
//     //             null
//     //     );
//     //     return ResponseEntity.status(status).body(response);
//     }
// //
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
// //    @PostMapping("/insert")
// //    public ResponseEntity<ApiResponse<UserDTO>> insertUser(@RequestBody Users user) {
// //        return userService.insertUser(user);
// //    }
// }
