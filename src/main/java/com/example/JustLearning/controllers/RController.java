package com.example.JustLearning.controllers;

import com.example.JustLearning.domain.Sights;
import com.example.JustLearning.domain.User;
import com.example.JustLearning.domain.VisitedSights;
import com.example.JustLearning.repository.SightsRepository;
import com.example.JustLearning.repository.VisitedRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;


@RestController
public class RController {
    private final VisitedRepository visitedRepository;
    private final SightsRepository sightsRepository;
    private final String jsonPath;

    public RController(SightsRepository sightsRepository, VisitedRepository visitedRepository, @Value("${json.path}") String jsonPath) {
        this.sightsRepository = sightsRepository;
        this.visitedRepository = visitedRepository;
        this.jsonPath = jsonPath;
    }

    @PostMapping
    public String addMySight(@RequestParam Sights id_sight, @AuthenticationPrincipal User user) {
        if (user != null) {
            if (!visitedRepository.existsByUserAndSight(user, id_sight)) {
                visitedRepository.save(new VisitedSights(id_sight, user, (float) 0.0));
                return "Достопримечательность добавлена!";
            }
            return "Достопримечательность уже была добавлена ранее!";
        } else {
            return "Необходимо авторизоваться!";
        }
    }

    @Transactional
    @PostMapping("/delmysight")
    public void deleteMySight(@RequestParam Sights id_sight, @AuthenticationPrincipal User user) {
        visitedRepository.deleteByUserAndSight(user, id_sight);
    }

    @PostMapping("/completemysight")
    public void completeMySight(@RequestParam Sights id_sight, @AuthenticationPrincipal User user, @RequestParam float rate) {
        VisitedSights sight = visitedRepository.findByUserAndSight(user, id_sight);
        sight.setRating(rate);
        sight.setVisited(true);
        visitedRepository.save(sight);
    }

    @GetMapping("/json")
    public String json() throws IOException {
        Iterable<Sights> allSights = sightsRepository.findAll();
        Sights current;
        FileWriter fileWriter = new FileWriter(jsonPath + "/new.json");
        String json = "{\n" +
                "    \"type\": \"FeatureCollection\",\n" +
                "    \"features\": [";
        Iterator iter = allSights.iterator();
        while (iter.hasNext()) {
            current = (Sights) iter.next();
            json += "        {\n" +
                    "            \"id\": " + current.getId() + ",\n" +
                    "            \"type\": \"Feature\",\n" +
                    "            \"geometry\":\n" +
                    "                {\n" +
                    "                    \"type\": \"Point\",\n" +
                    "                    \"coordinates\": [" + current.getGeom().getX() + "," + current.getGeom().getY() + "]},\n" +
                    "            \"properties\":\n" +
                    "                {\n" +
                    "                    \"balloonContentHeader\": \"" + current.getTitle() + "<br><span class='description'>" + current.getType_mark() + "</span>\",\n" +
                    "                    \"balloonContent\" : \"" + current.getType_mark() + "\",\n" +
                    "                    \"balloonContentBody\": \"<img style='width:15em' src='/img/" + current.getImage() + "'></br><b>" + current.getName_mark() + "</b></br>Оценка: <b>" + current.getRating() + "/5</b></br>" + current.getDescription() + "\",\n" +
                    "                    \"balloonContentFooter\": \"<form action='/' method='post'><input type='hidden' id='id_sight' value='" + current.getId() + "'><input type='hidden' name='_csrf' value='${_csrf.token}'><button type='button' id='add_sight' class='btn btn-dark'>Добавить</button></form>\",\n" +
                    "                    \"hintContent\": \"" + current.getTitle() + "'\"\n" +
                    "                },\n" +
                    "            \"options\":\n" +
                    "                {\n" +
                    "                    \"iconLayout\": \"default#image\",\n" +
                    "                    \"iconImageHref\" : \"/img/" + current.getIcon() + "\",\n" +
                    "                    \"iconImageSize\" : [40,40]\n" +
                    "                }\n" +
                    "        }\n";
            if (iter.hasNext()) {
                json += ",";
            }
        }
        json += "    ]\n" +
                "}";
        fileWriter.write(json);
        fileWriter.close();

        return json;
    }


}
