package com.ivaylo.exercise_tracker.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ExerciseRequest {
    private String name;
    private int repetitions;
    private LocalDate date;
}