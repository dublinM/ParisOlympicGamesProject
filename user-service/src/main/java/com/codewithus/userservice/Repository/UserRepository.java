package com.codewithus.userservice.Repository;


import com.codewithus.userservice.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    Optional<User> findByEmail(String email);  // Assuming email is unique

    List<User> findByAddress(String address);

    List<User> findByRole(String role);

    List<User> findByAge(int age);

    List<User> findByNationality(String nationality);

}
