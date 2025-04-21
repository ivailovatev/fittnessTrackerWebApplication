package com.ivaylo.exercise_tracker.repository;

import com.ivaylo.exercise_tracker.entity.Exercise;
import com.ivaylo.exercise_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findAllByUser(User user);
}
