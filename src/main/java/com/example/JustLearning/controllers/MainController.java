package com.example.JustLearning.controllers;

import com.example.JustLearning.domain.User;
import com.example.JustLearning.domain.VisitedSights;
import com.example.JustLearning.repository.UserRepository;
import com.example.JustLearning.repository.VisitedRepository;
import com.example.JustLearning.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Controller
public class MainController {

    private final UserRepository userRepository;
    private final VisitedRepository visitedRepository;
    private final UserService userService;

    public MainController(UserRepository userRepository, VisitedRepository visitedRepository, UserService userService, @Value("${upload.path}") String uploadPath) {
        this.userRepository = userRepository;
        this.visitedRepository = visitedRepository;
        this.userService = userService;
    }


    @GetMapping
    public String home() {
        return "home";
    }


    @GetMapping("/profile/{user}")
    public String profile(@PathVariable Long user, Model model) {
        Optional<User> user_profile = userRepository.findById(user);
        model.addAttribute("CurrentUser", user_profile);
        model.addAttribute("id_user", user);
        return "profile";
    }


    @GetMapping("/profile/edit/{id}")
    public String editProfile(@PathVariable Long id, Model model) {
        Optional<User> user_profile = userRepository.findById(id);
        model.addAttribute("CurrentUser", user_profile);
        model.addAttribute("id_user", id);
        return "profileEdit";
    }

    @PostMapping("/profile/edit")
    public String editUser(
            @RequestParam("userId") User user,
            @RequestParam("name") String name,
            @RequestParam("second_name") String second_name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("file") MultipartFile file,
            Model model
    ) throws IOException {
        return userService.editUserData(user, name, second_name, email, password, file, model);
    }

    @RequestMapping(value = "/mysights", method = RequestMethod.GET)
    public String MySigths(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            List<VisitedSights> sights = visitedRepository.findByUserAndVisited(user, false);
            model.addAttribute("sights", sights);
            return "mysights";
        }
        return "needauto";

    }
}
