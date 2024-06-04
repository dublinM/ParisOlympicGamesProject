package com.codewithus.sportservice.Repository;

import com.codewithus.sportservice.Model.Sport;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SportRepository extends MongoRepository<Sport, String> {
    Optional<Sport> findByName(String name);

    List<Sport> findByCategory(String category);

    List<Sport> findByGender(String gender);

    Optional<Sport> findByDescription(String description);
}
