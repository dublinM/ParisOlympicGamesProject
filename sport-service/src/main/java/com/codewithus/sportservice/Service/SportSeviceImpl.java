package com.codewithus.sportservice.Service;

import com.codewithus.sportservice.Dto.SportRequest;
import com.codewithus.sportservice.Dto.SportResponse;
import com.codewithus.sportservice.Model.Sport;
import com.codewithus.sportservice.Repository.SportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SportSeviceImpl implements SportService{

    private final SportRepository sportRepository;

    public void createSport(SportRequest sportRequest) {
        Sport sport = Sport.builder()
                .name(sportRequest.getName())
                .description(sportRequest.getDescription())
                .category(sportRequest.getCategory())
                .gender(sportRequest.getGender())
                .build();

        sportRepository.save(sport);
        log.info("Sport {} is saved", sport.getId());

    }

    public void createSports(List<SportRequest> sportRequests) {
        List<Sport> sports = sportRequests.stream()
                .map(sportRequest -> Sport.builder()
                        .name(sportRequest.getName())
                        .description(sportRequest.getDescription())
                        .category(sportRequest.getCategory())
                        .gender(sportRequest.getGender())
                        .build())
                .collect(Collectors.toList());
        sportRepository.saveAll(sports);
    }

    public List<SportResponse> getAllSports() {
        List<Sport> sports = sportRepository.findAll();
        return sports.stream().map(this::mapToSportResponse).toList();
    }

    public SportResponse getSportByName(String name) {
        Sport sport = sportRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Sport not found with name: " + name));

        return mapToSportResponse(sport);
    }

    public List<SportResponse> getSportByCategory(String category) {
        List<Sport> sports = sportRepository.findByCategory(category);
        if (sports.isEmpty()) {
            throw new RuntimeException("No sports found for category: " + category);
        }
        return sports.stream()
                .map(this::mapToSportResponse)
                .collect(Collectors.toList());
    }

    public List<SportResponse> getSportByGender(String gender) {
        List<Sport> sports = sportRepository.findByGender(gender);
        if (sports.isEmpty()) {
            throw new RuntimeException("No sports found for gender: " + gender);
        }
        return sports.stream()
                .map(this::mapToSportResponse)
                .collect(Collectors.toList());
    }

    public SportResponse getSportBydescription(String description) {
        Sport sport = sportRepository.findByDescription(description)
                .orElseThrow(() -> new RuntimeException("We cannot find a sport with the description you provided "));
        return mapToSportResponse(sport);
    }

    public void deleteSport(String id) {
        sportRepository.deleteById(id);
    }

    public SportResponse updateSport(String id, SportRequest sportRequest) {
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sport not found with id: " + id));

        sport.setName(sportRequest.getName());
        sport.setDescription(sportRequest.getDescription());
        sport.setCategory(sportRequest.getCategory());
        sport.setGender(sportRequest.getGender());

        sportRepository.save(sport);
        return mapToSportResponse(sport);
    }

    public SportResponse updateSportFields(String id, Map<String,Object> updates) {
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sport not found with id: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    sport.setName((String) value);
                    break;
                case "description":
                    sport.setDescription((String) value);
                    break;
                case "category":
                    sport.setCategory((String) value);
                    break;
                case "gender":
                    sport.setGender((String) value);
                    break;
            }
        });

        sportRepository.save(sport);
        return mapToSportResponse(sport);
    }

    public SportResponse getSportById(String id) {
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sport not found with id: " + id));
        return mapToSportResponse(sport);
    }

    private SportResponse mapToSportResponse(Sport sport) {
        return SportResponse.builder()
                .id(sport.getId())
                .name(sport.getName())
                .description(sport.getDescription())
                .category(sport.getCategory())
                .gender(sport.getGender())
                .build();
    }
}
