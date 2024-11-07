package com.example.crud;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class StudentController {

    @Autowired
    private UserRepository userRepository;

    // GET method to retrieve user by email
    @GetMapping("/getUser")
    public ResponseEntity<?> getUserByEmail(@RequestParam(name="email") String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with the given email not found");
    }

    // POST method to create a new user
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists.");
        }
        User newUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
