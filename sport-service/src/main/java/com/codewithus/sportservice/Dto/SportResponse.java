package com.codewithus.sportservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SportResponse {

    private String id;
    private String name;
    private String category;
    private String description;
    private String gender;

}
