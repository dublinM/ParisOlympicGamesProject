package com.codewithus.eventservice.Model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_name", nullable = false, unique = true)
    private String name;

    @Column(name = "date")
    private Date date;

    @Column(name = "description")
    private String description;

    @Column(name = "sport_id", nullable = false)
    private String sportId;

    @Column(name = "site_id", nullable = false)
    private Long siteId;

}
