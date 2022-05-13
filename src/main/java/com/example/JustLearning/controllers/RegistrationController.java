package com.example.JustLearning.controllers;

import com.example.JustLearning.domain.User;
import com.example.JustLearning.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class RegistrationController {
    private final UserService userService;
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, @RequestParam("file") MultipartFile file, Model model) throws IOException {
        if (userService.addNewUser(user, file, model)) return "registration";
        return "redirect:/";
    }


}
