package com.codewithus.planningservice.Controller;

import com.codewithus.planningservice.Dto.PlanningDto;
import com.codewithus.planningservice.Service.PlanningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/planning")
@RequiredArgsConstructor
public class PlanningController {

    private final PlanningService planningService;

    @PostMapping
    public ResponseEntity<PlanningDto> createPlanning(@RequestBody PlanningDto planningDto) {
        PlanningDto createdPlanning = planningService.createPlanning(planningDto);
        return ResponseEntity.ok(createdPlanning);
    }

    @GetMapping
    public ResponseEntity<List<PlanningDto>> getAllPlannings() {
        List<PlanningDto> plannings = planningService.getAllPlannings();
        return ResponseEntity.ok(plannings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanningDto> getPlanningById(@PathVariable Long id) {
        PlanningDto planning = planningService.getPlanningById(id);
        return ResponseEntity.ok(planning);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanningDto> updatePlanning(@PathVariable Long id, @RequestBody PlanningDto planningDto) {
        PlanningDto updatedPlanning = planningService.updatePlanning(id, planningDto);
        return ResponseEntity.ok(updatedPlanning);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanning(@PathVariable Long id) {
        planningService.deletePlanning(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<PlanningDto>> getPlanningsByEventId(@PathVariable Long eventId) {
        List<PlanningDto> plannings = planningService.getPlanningsByEventId(eventId);
        return ResponseEntity.ok(plannings);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PlanningDto>> getPlanningsByUserId(@PathVariable String userId) {
        List<PlanningDto> plannings = planningService.getPlanningsByUserId(userId);
        return ResponseEntity.ok(plannings);
    }
}
