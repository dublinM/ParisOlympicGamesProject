package com.codewithus.sportservice.Controller;

import com.codewithus.sportservice.Dto.SportRequest;
import com.codewithus.sportservice.Dto.SportResponse;
import com.codewithus.sportservice.Service.SportSeviceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sport")
@RequiredArgsConstructor
public class SportController {

    private final SportSeviceImpl sportService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSport(@RequestBody SportRequest sportRequest) {
        sportService.createSport(sportRequest);
    }

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSports(@RequestBody List<SportRequest> sportRequests) {
        sportService.createSports(sportRequests);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public SportResponse getSportById(@PathVariable String id) {
        return sportService.getSportById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SportResponse> getAllSports() {
        return sportService.getAllSports();
    }

    @GetMapping("/name")
    @ResponseStatus(HttpStatus.OK)
    public SportResponse getSportByName(@RequestParam("name") String name) { return sportService.getSportByName(name);}

    @GetMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    public List<SportResponse> getSportByCategory(@RequestParam("category") String category) { return sportService.getSportByCategory(category);}

    @GetMapping("/gender")
    @ResponseStatus(HttpStatus.OK)
    public List<SportResponse> getSportByGender(@RequestParam("gender") String gender) { return sportService.getSportByGender(gender);}

    @GetMapping("/description")
    @ResponseStatus(HttpStatus.OK)
    public SportResponse getSportByDescription(@RequestParam("description") String description) { return sportService.getSportBydescription(description);}

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSport(@PathVariable String id) {
        sportService.deleteSport(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SportResponse updateSport(@PathVariable String id, @RequestBody SportRequest sportRequest) {
        return sportService.updateSport(id, sportRequest);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SportResponse updateSportFields(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        return sportService.updateSportFields(id, updates);
    }
}
