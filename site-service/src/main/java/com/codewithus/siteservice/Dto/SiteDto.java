package com.codewithus.siteservice.Dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SiteDto {

    private Long id;
    private String name;
    @JsonProperty("isParalympic")
    private boolean isParalympic;
    private String address;
}
