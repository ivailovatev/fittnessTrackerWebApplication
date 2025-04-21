package com.ivaylo.exercise_tracker.controller;

import com.ivaylo.exercise_tracker.dto.ExerciseRequest;
import com.ivaylo.exercise_tracker.dto.ExerciseResponse;
import com.ivaylo.exercise_tracker.dto.RegisterRequest;
import com.ivaylo.exercise_tracker.security.JwtUtil;
import com.ivaylo.exercise_tracker.service.AuthService;
import com.ivaylo.exercise_tracker.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private AuthService authService;

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterRequest registerRequest, Model model) {
        try {
            authService.register(registerRequest);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("registerRequest", registerRequest);
            return "register";
        }
    }


    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            Model model) {
        try {
            String token = authService.login(email, password); // ще добавим метод долу
            model.addAttribute("token", token);
            return "redirect:/exercises?token=" + token;
        } catch (RuntimeException e) {
            model.addAttribute("error", "Невалидни данни за вход");
            return "login";
        }
    }

    @GetMapping("/exercises")
    public String showExercises(Model model, @RequestParam(name = "token", required = false) String token) {
        if (token == null || !jwtUtil.validateToken(token)) {
            return "redirect:/login";
        }

        List<ExerciseResponse> exercises = exerciseService.getAllExercises();
        model.addAttribute("exercises", exercises);
        model.addAttribute("token", token);
        return "exercises";
    }


    @PostMapping("/exercises/add")
    public String addExercise(@RequestParam String name,
                              @RequestParam int repetitions,
                              @RequestParam String date,
                              @RequestParam String token) {

        if (token == null || !jwtUtil.validateToken(token)) {
            return "redirect:/login";
        }

        String email = jwtUtil.extractUsername(token);
        ExerciseRequest request = new ExerciseRequest();
        request.setName(name);
        request.setRepetitions(repetitions);
        request.setDate(LocalDate.parse(date));

        exerciseService.addExerciseForUser(email, request);
        return "redirect:/exercises?token=" + token;
    }

    @PostMapping("/exercises/delete/{id}")
    public String deleteExercise(@PathVariable Long id,
                                 @RequestParam String token) {

        if (token == null || !jwtUtil.validateToken(token)) {
            return "redirect:/login";
        }

        exerciseService.deleteExercise(id);
        return "redirect:/exercises?token=" + token;
    }

}