package com.example.JustLearning.controllers;

import com.example.JustLearning.domain.Role;
import com.example.JustLearning.domain.Sights;
import com.example.JustLearning.domain.User;
import com.example.JustLearning.repository.SightsRepository;
import com.example.JustLearning.repository.UserRepository;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin-panel")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SightsRepository sightsRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${json.path}")
    private String jsonPath;

    @GetMapping
    public String adminpanel(){
        return "admin-panel";
    }

    @GetMapping("userlist")
    public String userList(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

    @GetMapping("userlist/{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping("userlist")
    public String userSave(@RequestParam("userId") User user, @RequestParam Map<String, String> form, @RequestParam String username){
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        user.getRoles().clear();

        for(String key : form.keySet()){
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);
        return "redirect:/admin-panel/userlist";
    }

    @GetMapping("sightslist")
    public String sigthsList(Model model){
        model.addAttribute("sights", sightsRepository.findAll());
        return "sightsList";
    }

    @GetMapping("addsight")
    public String addSight(){
        return "addSight";
    }

    @PostMapping("addsight")
    public String saveSight(Sights sight, @RequestParam("file") MultipartFile file, @RequestParam String geo) throws IOException, ParseException {
        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + "/" + resultFilename));

        WKTReader reader = new WKTReader();
        sight.setGeom((Point) reader.read(geo));
        sight.setImage(resultFilename);
        sight.setRating((float) 0.0);
        switch (sight.getType_mark()){
            case "??????????":
                sight.setIcon("fe12e71b-94d5-40f6-a3fc-0c00af30fac2.museum.png");
                break;
            case "????????????????":
                sight.setIcon("fe12e71b-94d5-40f6-a3fc-0c00af30fac5.monument.png");
                break;
            case "????????":
                sight.setIcon("fe12e71b-94d5-40f6-a3fc-0c00af30fac1.church.png");
                break;
            case "??????????????":
                sight.setIcon("fe12e71b-94d5-40f6-a3fc-0c00af30fac3.nature.png");
                break;
            case "????????????????????":
                sight.setIcon("fe12e71b-94d5-40f6-a3fc-0c00af30fac4.sculpture.png");
                break;
            case "???????????????????? ????????????????":
                sight.setIcon("fe12e71b-94d5-40f6-a3fc-0c00af30fa44.cult.png");
                break;
        }
        sightsRepository.save(sight);
        Iterable<Sights> allSights = sightsRepository.findAll();
        Sights current;
        FileWriter fileWriter = new FileWriter(jsonPath + "/new.json");
        String json = "{\n" +
                "    \"type\": \"FeatureCollection\",\n" +
                "    \"features\": [";
        Iterator iter = allSights.iterator();
        while (iter.hasNext()){
            current = (Sights) iter.next();
            json += "        {\n" +
                    "            \"id\": " + current.getId() + ",\n" +
                    "            \"type\": \"Feature\",\n" +
                    "            \"geometry\":\n" +
                    "                {\n" +
                    "                    \"type\": \"Point\",\n" +
                    "                    \"coordinates\": ["+ current.getGeom().getX() + "," + current.getGeom().getY() + "]},\n" +
                    "            \"properties\":\n" +
                    "                {\n" +
                    "                    \"balloonContentHeader\": \""+ current.getTitle() +"<br><span class='description'>" + current.getType_mark() + "</span>\",\n" +
                    "                    \"balloonContent\" : \"" + current.getType_mark() + "\",\n" +
                    "                    \"balloonContentBody\": \"<img style='width:15em' src='/img/" + current.getImage() + "'></br><b>" + current.getName_mark() + "</b></br>????????????: <b>" + current.getRating() + "/5</b></br>" + current.getDescription() + "\",\n" +
                    "                    \"balloonContentFooter\": \"<form action=\"/\" method=\"post\"><input type=\"hidden\" name=\"id_sight\" value=\"" + current.getId() + "\"><input type=\"hidden\" name=\"_csrf\" value=\"${_csrf.token}\"><button class='btn btn-dark'>????????????????</button></form>\",\n" +
                    "                    \"hintContent\": \"" + current.getTitle() + "'\"\n" +
                    "                },\n" +
                    "            \"options\":\n" +
                    "                {\n" +
                    "                    \"iconLayout\": \"default#image\",\n" +
                    "                    \"iconImageHref\" : \"/img/" + current.getIcon() + "\",\n" +
                    "                    \"iconImageSize\" : [40,40]\n" +
                    "                }\n" +
                    "        }\n";
            if (iter.hasNext()){
                json += ",";
            }
        }
        json += "    ]\n" +
                "}";
        fileWriter.write(json);
        fileWriter.close();
        return "redirect:/admin-panel/addsight";
    }

    @GetMapping("sightslist/{id}")
    public String editSight(@PathVariable Integer id, Model model){
        Optional<Sights> sight = sightsRepository.findById(id);
        model.addAttribute("sight", sight);
        return "sightEdit";
    }

    @PostMapping("sightslist")
    public String saveEditSight(
            @RequestParam("id_sight") Sights sight,
            @RequestParam("title") String title,
            @RequestParam("name_mark") String name_mark,
            @RequestParam("type_mark") String type_mark,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile file
    ) throws IOException {
        sight.setTitle(title);
        sight.setName_mark(name_mark);
        sight.setDescription(description);
        if(!file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            sight.setImage(resultFilename);
        }
        sight.setType_mark(type_mark);
        switch (type_mark){
            case "??????????":
                sight.setIcon("fe12e71b-94d5-40f6-a3fc-0c00af30fac2.museum.png");
                break;
            case "????????????????":
                sight.setIcon("fe12e71b-94d5-40f6-a3fc-0c00af30fac5.monument.png");
                break;
            case "????????":
                sight.setIcon("fe12e71b-94d5-40f6-a3fc-0c00af30fac1.church.png");
                break;
            case "??????????????":
                sight.setIcon("fe12e71b-94d5-40f6-a3fc-0c00af30fac3.nature.png");
                break;
            case "????????????????????":
                sight.setIcon("fe12e71b-94d5-40f6-a3fc-0c00af30fac4.sculpture.png");
                break;
            case "???????????????????? ????????????????":
                sight.setIcon("fe12e71b-94d5-40f6-a3fc-0c00af30fa44.cult.png");
                break;
        }
        sightsRepository.save(sight);
        return "redirect:sightslist/";
    }
}

