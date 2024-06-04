package com.codewithus.userservice.Controller;


import com.codewithus.userservice.Dto.UserRequest;
import com.codewithus.userservice.Dto.UserResponse;
import com.codewithus.userservice.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
    }

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUsers(@RequestBody List<UserResponse> userRequests) {
        userService.createUsers(userRequests);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateUserById(@PathVariable String id, @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateUsedFields(@PathVariable String id, @RequestBody Map<String,Object> updates) {
        return userService.updateUserFields(id, updates);
    }

    @GetMapping("/firstName/{firstName}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getUsersByFirstName(@PathVariable String firstName) {
        return userService.getUsersByFirstName(firstName);
    }

    @GetMapping("/lastName/{lastName}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getUsersByLastName(@PathVariable String lastName) {
        return userService.getUsersByLastName(lastName);
    }

    @GetMapping("/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/address/{address}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getUsersByAddress(@PathVariable String address) {
        return userService.getUsersByAddress(address);
    }

    @GetMapping("/role/{role}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getUsersByRole(@PathVariable String role) {
        return userService.getUsersByRole(role);
    }

    @GetMapping("/age/{age}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getUsersByAge(@PathVariable int age) {
        return userService.getUsersByAge(age);
    }

    @GetMapping("/nationality/{nationality}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getUsersByNationality(@PathVariable String nationality) {
        return userService.getUsersByNationality(nationality);
    }
}
