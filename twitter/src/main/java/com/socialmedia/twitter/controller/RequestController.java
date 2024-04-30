package com.socialmedia.twitter.controller;


import com.socialmedia.twitter.model.User;
import com.socialmedia.twitter.model.UserDTO;
import com.socialmedia.twitter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j

@RestController
@RequestMapping("")
public class RequestController {
    @Autowired
    UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = UserDTO.mapToDTOList(userService.getAllUsers());
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }



    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody User user) {
        if (!userService.isUserExists(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }

        if (userService.authenticateUser(user)) {
            return ResponseEntity.ok("Login Successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username/Password Incorrect");
        }
    }



    @PostMapping("/signup")
        public ResponseEntity<String> signupUser(@RequestBody User user) {
        if (userService.isUserExists(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden, Account already exists");
        }
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Account Creation Successful");
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUserById(@RequestParam int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
        }

        UserDTO userDTO = UserDTO.mapToDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
