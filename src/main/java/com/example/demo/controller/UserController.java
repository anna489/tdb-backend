package com.example.demo.controller;

import com.example.demo.model.AppUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AppUser user) {
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("This username is taken");
        }

        AppUser createdUser = userService.signup(user);
        return ResponseEntity.ok().body(Map.of("message", "User created successfully", "user", createdUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AppUser user) {
        AppUser loggedInUser = userService.login(user.getUsername(), user.getPassword());
        if (loggedInUser != null) {
            return ResponseEntity.ok(loggedInUser);
        }
        return ResponseEntity.status(401).build(); // Unauthorized
    }

    @GetMapping("/getAllUsers")
    public List<AppUser> getAllUsers() {
        return userService.getAllUsers();
    }
}
