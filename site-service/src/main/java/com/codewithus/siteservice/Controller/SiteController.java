package com.codewithus.siteservice.Controller;

import com.codewithus.siteservice.Dto.SiteDto;
import com.codewithus.siteservice.Service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/site")
public class SiteController {

    private final SiteService siteService;


    @PostMapping
    public ResponseEntity<SiteDto> createSite(@RequestBody SiteDto siteDto) {
        SiteDto savedSite = siteService.createSite(siteDto);
        return new ResponseEntity<>(savedSite, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<SiteDto>> createSites(@RequestBody List<SiteDto> siteDtos) {
        List<SiteDto> savedSites = siteService.createSites(siteDtos);
        return new ResponseEntity<>(savedSites, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteDto> getSiteById(@PathVariable Long id) {
        SiteDto siteDto = siteService.getSiteById(id);
        return ResponseEntity.ok(siteDto);
    }

    @GetMapping("/name")
    public ResponseEntity<SiteDto> getSitesByName(@RequestParam("name") String name) {
        SiteDto site = siteService.getSitesByName(name);
        return ResponseEntity.ok(site);
    }

    @GetMapping("/address")
    public ResponseEntity<List<SiteDto>> getSitesByAddress(@RequestParam("address")String address) {
        List<SiteDto> sites = siteService.getSitesByAddress(address);
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/isParalympic")
    public ResponseEntity<List<SiteDto>> getSitesByIsParalympic(@RequestParam("isParalympic") boolean isParalympic) {
        List<SiteDto> sites = siteService.getSitesByIsParalympic(isParalympic);
        return ResponseEntity.ok(sites);
    }

    @GetMapping
    public ResponseEntity<List<SiteDto>> getAllSites() {
        List<SiteDto> sites = siteService.getAllSites();
        return ResponseEntity.ok(sites);
    }


    @PutMapping("/{id}")
    public ResponseEntity<SiteDto> updateSite(@PathVariable Long id, @RequestBody SiteDto siteDto) {
        SiteDto siteDto1 = siteService.updateSite(id, siteDto);
        return ResponseEntity.ok(siteDto1);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<SiteDto> updateSiteField(@PathVariable Long id, @RequestBody Map<String, Object> updateFields) {
        if (updateFields.size() != 1) {
            throw new IllegalArgumentException("Only one field can be updated at a time");
        }

        Map.Entry<String, Object> field = updateFields.entrySet().iterator().next();
        SiteDto updatedSite = siteService.updateSiteField(id, field.getKey(), field.getValue());
        return ResponseEntity.ok(updatedSite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSite(@PathVariable Long id) {
        siteService.deleteSite(id);
        return ResponseEntity.ok("Site deleted successfully");
    }

}
