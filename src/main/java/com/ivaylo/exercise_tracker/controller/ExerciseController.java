package com.ivaylo.exercise_tracker.controller;

import com.ivaylo.exercise_tracker.dto.ExerciseRequest;
import com.ivaylo.exercise_tracker.dto.ExerciseResponse;
import com.ivaylo.exercise_tracker.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;

    @PostMapping
    public ResponseEntity<Void> addExercise(@RequestBody ExerciseRequest request) {
        exerciseService.addExercise(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ExerciseResponse>> getAllExercises() {
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.ok().build();
    }
}
