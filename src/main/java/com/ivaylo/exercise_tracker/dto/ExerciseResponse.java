package com.ivaylo.exercise_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class ExerciseResponse {
    private Long id;
    private String name;
    private int repetitions;
    private LocalDate date;
}
