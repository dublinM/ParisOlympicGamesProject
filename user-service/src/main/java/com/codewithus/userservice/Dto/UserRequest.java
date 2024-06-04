package com.codewithus.userservice.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String role;
    private int age;
    private String nationality;
}
