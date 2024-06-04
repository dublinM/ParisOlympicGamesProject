package com.codewithus.userservice.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String role;
    private int age;
    private String nationality;


}
