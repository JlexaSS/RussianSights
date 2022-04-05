package com.example.JustLearning.controllers;

import com.example.JustLearning.domain.Role;
import com.example.JustLearning.domain.User;
import com.example.JustLearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Controller
public class RegistrationController {
    @Autowired
    UserRepository userRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, @RequestParam("file") MultipartFile file, Model model) throws IOException {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null){
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        if(file.getOriginalFilename().isEmpty()){
            user.setImgProfile("68e2b43f-0a8f-4ed6-97a7-9fde4314e703.profileimage.png");
        } else{
            File uploadDir = new File(uploadPath);

            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            user.setImgProfile(resultFilename);
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/";
    }
}
