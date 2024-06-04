package com.codewithus.planningservice.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "planning")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Planning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planning_id")
    private Long id;
    
    @Column(name = "user_id")
    private String userId;

    @Column(name = "event_id")
    private Long eventId;
}
