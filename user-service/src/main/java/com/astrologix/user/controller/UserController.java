package com.astrologix.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public List<String> getUsers() {
        return Collections.emptyList(); // Return an empty list for now
    }
}
