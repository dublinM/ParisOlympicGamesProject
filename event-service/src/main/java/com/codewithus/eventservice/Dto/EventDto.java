package com.codewithus.eventservice.Dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    private Long id;
    private String name;
    private Date date;
    private String description;
    private String sportId;
    private Long siteId;

}
