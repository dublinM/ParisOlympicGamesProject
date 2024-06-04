package com.codewithus.sportservice.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "sport")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Sport {

    @Id
    private String id;
    private String name;
    private String category;
    private String description;
    private String gender;

}
