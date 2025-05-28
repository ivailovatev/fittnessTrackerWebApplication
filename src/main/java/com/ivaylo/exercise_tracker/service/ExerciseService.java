package com.ivaylo.exercise_tracker.service;

import com.ivaylo.exercise_tracker.dto.ExerciseRequest;
import com.ivaylo.exercise_tracker.dto.ExerciseResponse;
import com.ivaylo.exercise_tracker.entity.Exercise;
import com.ivaylo.exercise_tracker.entity.User;
import com.ivaylo.exercise_tracker.repository.ExerciseRepository;
import com.ivaylo.exercise_tracker.repository.UserRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Builder
@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public void addExercise(ExerciseRequest request) {
        User user = getCurrentUser();
        Exercise exercise = Exercise.builder()
                .name(request.getName())
                .repetitions(request.getRepetitions())
                .date(request.getDate())
                .user(user)
                .build();
        exerciseRepository.save(exercise);
    }

    public List<ExerciseResponse> getAllExercises() {
        User user = getCurrentUser();
        return exerciseRepository.findAllByUser(user).stream()
                .map(ex -> new ExerciseResponse(ex.getId(), ex.getName(), ex.getRepetitions(), ex.getDate()))
                .collect(Collectors.toList());
    }

    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }

    public void addExerciseForUser(String email, ExerciseRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Exercise exercise = Exercise.builder()
                .name(request.getName())
                .repetitions(request.getRepetitions())
                .date(request.getDate())
                .user(user)
                .build();
        exerciseRepository.save(exercise);
    }
    public void updateExercise(Long id, ExerciseRequest request) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        exercise.setName(request.getName());
        exercise.setRepetitions(request.getRepetitions());
        exercise.setDate(request.getDate());

        exerciseRepository.save(exercise);
    }

    public ExerciseResponse getExerciseById(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Упражнението не е намерено"));

        return ExerciseResponse.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .repetitions(exercise.getRepetitions())
                .date(exercise.getDate())
                .build();
    }


}
