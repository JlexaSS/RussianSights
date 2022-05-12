package com.example.JustLearning.controllers;

import com.example.JustLearning.domain.User;
import com.example.JustLearning.domain.VisitedSights;
import com.example.JustLearning.repository.UserRepository;
import com.example.JustLearning.repository.VisitedRepository;
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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Controller
public class MainController {

    private final UserRepository userRepository;
    private final VisitedRepository visitedRepository;
    private final String uploadPath;

    public MainController(UserRepository userRepository, VisitedRepository visitedRepository, @Value("${upload.path}") String uploadPath) {
        this.userRepository = userRepository;
        this.visitedRepository = visitedRepository;
        this.uploadPath = uploadPath;
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
        String truePas = user.getPassword();
        if (truePas.equals(password)) {
            user.setName(name);
            user.setSecond_name(second_name);
            user.setEmail(email);
            if (!file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadPath + "/" + resultFilename));

                user.setImgProfile(resultFilename);
            }
            userRepository.save(user);

            return "redirect:/profile/" + user.getId();
        } else {
            model.addAttribute("error", "Пароль пуст или введен неверно!");
            return "redirect:/profile/edit/" + user.getId();
        }
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
