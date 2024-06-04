package com.codewithus.planningservice.Repository;

import com.codewithus.planningservice.Model.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlanningRepository extends JpaRepository<Planning, Long> {

    List<Planning> findByEventId(Long eventId);
    List<Planning> findByUserId(String userId);

}
