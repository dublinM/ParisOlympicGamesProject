package com.codewithus.sportservice.Service;

import com.codewithus.sportservice.Dto.SportRequest;
import com.codewithus.sportservice.Dto.SportResponse;

import java.util.List;
import java.util.Map;

public interface SportService {

    public void createSport(SportRequest sportRequest);

    public void createSports(List<SportRequest> sportRequests);

    public List<SportResponse> getAllSports();

    public SportResponse getSportByName(String name);

    public List<SportResponse> getSportByCategory(String category);

    public List<SportResponse> getSportByGender(String gender);

    public SportResponse getSportBydescription(String description);

    public void deleteSport(String id);

    public SportResponse updateSport(String id, SportRequest sportRequest);

    public SportResponse updateSportFields(String id, Map<String,Object> updates);

    public SportResponse getSportById(String id);
}
