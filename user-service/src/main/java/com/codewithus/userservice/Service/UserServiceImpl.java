package com.codewithus.userservice.Service;

import com.codewithus.userservice.Dto.UserRequest;
import com.codewithus.userservice.Dto.UserResponse;
import com.codewithus.userservice.Exception.ResourceNotFoundException;
import com.codewithus.userservice.Model.User;
import com.codewithus.userservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserResponse).toList();
    }

    @Override
    public void createUser(UserRequest userRequest) {
        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .age(userRequest.getAge())
                .address(userRequest.getAddress())
                .role(userRequest.getRole())
                .nationality(userRequest.getNationality())
                .build();

        userRepository.save(user);
        log.info("User has been saved !");

    }

    @Override
    public void createUsers(List<UserResponse> userRequests) {

        List<User> users = userRequests.stream()
                .map(userRequest -> User.builder()
                        .firstName(userRequest.getFirstName())
                        .lastName(userRequest.getLastName())
                        .email(userRequest.getEmail())
                        .age(userRequest.getAge())
                        .address(userRequest.getAddress())
                        .role(userRequest.getRole())
                        .nationality(userRequest.getNationality())
                        .build())
                .collect(Collectors.toList());
        userRepository.saveAll(users);
        log.info("Users have been saved successfully !");
    }

    @Override
    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow( () ->  new RuntimeException("User not found with id: " + id));
        return mapToUserResponse(user);
    }


    @Override
    public void deleteUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow( () ->  new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }

    @Override
    public UserResponse updateUser(String id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User Not found !"));


        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setAge(userRequest.getAge());
        user.setEmail(userRequest.getEmail());
        user.setNationality(user.getNationality());
        user.setAddress(userRequest.getAddress());
        user.setRole(userRequest.getRole());

        userRepository.save(user);
        return mapToUserResponse(user);
    }

    @Override
    public UserResponse updateUserFields(String id, Map<String,Object> updates) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not found !"));
        updates.forEach((key, value) -> {
            switch (key) {
                case "firstName" :
                    user.setFirstName((String) value);
                    break;
                case "lastName":
                    user.setLastName((String) value);
                    break;
                case "age":
                    user.setAge((int) value);
                    break;
                case "role":
                    user.setRole((String) value);
                    break;
                case "email":
                    user.setEmail((String) value);
                    break;
                case "address":
                    user.setAddress((String) value);
                    break;
                case "nationality":
                    user.setNationality((String) value);
                    break;

            }
        });

        userRepository.save(user);
        return mapToUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsersByFirstName(String firstName) {
        List<User> users = userRepository.findByFirstName(firstName);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found with first name: " + firstName);
        }
        return users.stream().map(this::mapToUserResponse).collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getUsersByLastName(String lastName) {
        List<User> users = userRepository.findByLastName(lastName);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found with last name: " + lastName);
        }
        return users.stream().map(this::mapToUserResponse).collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return mapToUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsersByAddress(String address) {
        List<User> users = userRepository.findByAddress(address);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found with address: " + address);
        }
        return users.stream().map(this::mapToUserResponse).collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getUsersByRole(String role) {
        List<User> users = userRepository.findByRole(role);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found with role: " + role);
        }
        return users.stream().map(this::mapToUserResponse).collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getUsersByAge(int age) {
        List<User> users = userRepository.findByAge(age);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found at age: " + age);
        }
        return users.stream().map(this::mapToUserResponse).collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getUsersByNationality(String nationality) {
        List<User> users = userRepository.findByNationality(nationality);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found with nationality: " + nationality);
        }
        return users.stream().map(this::mapToUserResponse).collect(Collectors.toList());
    }


    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .age(user.getAge())
                .address(user.getAddress())
                .role(user.getRole())
                .nationality(user.getNationality())
                .build();
    }
}
