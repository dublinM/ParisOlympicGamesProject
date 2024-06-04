package com.codewithus.eventservice.Repository;

import com.codewithus.eventservice.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByName(String name);

    Optional<Event> findByDescription(String description);

    List<Event> findBySportId(String sportId);

    List<Event> findBySiteId(Long siteId);

    List<Event> findByDate(Date date);
}
