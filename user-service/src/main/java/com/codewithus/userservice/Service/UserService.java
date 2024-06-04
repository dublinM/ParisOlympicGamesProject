package com.codewithus.userservice.Service;

import com.codewithus.userservice.Dto.UserRequest;
import com.codewithus.userservice.Dto.UserResponse;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserResponse> getAllUsers();

    void createUser(UserRequest userRequest);

    UserResponse getUserById(String id);

    void createUsers(List<UserResponse> userRequests);

    void deleteUserById(String id);

    UserResponse updateUser(String id, UserRequest userRequest);

    UserResponse updateUserFields(String id, Map<String,Object> updates);

    List<UserResponse> getUsersByFirstName(String firstName);

    List<UserResponse> getUsersByLastName(String lastName);

    UserResponse getUserByEmail(String email);

    List<UserResponse> getUsersByAddress(String address);

    List<UserResponse> getUsersByRole(String role);

    List<UserResponse> getUsersByAge(int age);

    List<UserResponse> getUsersByNationality(String nationality);
}
