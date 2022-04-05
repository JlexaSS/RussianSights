package com.example.JustLearning.controllers;

import com.example.JustLearning.domain.Message;
import com.example.JustLearning.domain.Sights;
import com.example.JustLearning.domain.User;
import com.example.JustLearning.domain.VisitedSights;
import com.example.JustLearning.repository.MessageRepository;
import com.example.JustLearning.repository.SightsRepository;
import com.example.JustLearning.repository.UserRepository;
import com.example.JustLearning.repository.VisitedRepository;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Controller
public class MainController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VisitedRepository visitedRepository;

    @Value("${upload.path}")
    private String uploadPath;



    @GetMapping
    public String home(){
        return "home";
    }



    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model){
        Iterable<Message> messages = messageRepository.findAll();

        if (filter != null && !filter.isEmpty()){
            messages = messageRepository.findByTag(filter);
        } else{
            messages = messageRepository.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        Message message = new Message(text, tag, user);
        if(file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);

            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }
        messageRepository.save(message);
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "main";
    }


    @GetMapping("/profile/{user}")
    public String profile(@PathVariable Long user, Model model){
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
    ) throws IOException
    {
        String truePas = user.getPassword();
        if(truePas.equals(password)){
            user.setName(name);
            user.setSecond_name(second_name);
            user.setEmail(email);
            if(!file.getOriginalFilename().isEmpty()) {
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

            return "redirect:/profile/"+user.getId();
        } else{
            model.addAttribute("error", "Пароль пуст или введен неверно!");
            return "redirect:/profile/edit/" + user.getId();
        }
    }

    @RequestMapping(value = "/mysights", method = RequestMethod.GET)
    public String MySigths(@AuthenticationPrincipal User user, Model model){
        if (user != null){
            List<VisitedSights> sights = visitedRepository.findByUserAndVisited(user, false);
            model.addAttribute("sights", sights);
            return "mysights";
        }
        return "needauto" ;

    }
}
