package com.example.JustLearning.service;

import com.example.JustLearning.domain.Sights;
import com.example.JustLearning.enums.markTypes;
import com.example.JustLearning.repository.SightsRepository;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class SightsService {
    private final SightsRepository sightsRepository;

    public SightsService(SightsRepository sightsRepository) {
        this.sightsRepository = sightsRepository;
    }

    public void addNewSight(Sights sight, markTypes type, MultipartFile file, String geo) throws IOException, ParseException {
        sightsImageUpload(sight, file);
        WKTReader reader = new WKTReader();
        sight.setGeom((Point) reader.read(geo));
        sight.setRating((float) 0.0);
        sight.setType_mark(type.getCategory());
        sight.setIcon(type.getIconPath());
        sightsRepository.save(sight);
    }

    public void editSights(Sights sight, String title, String name_mark, markTypes type, String description, MultipartFile file) throws IOException {
        sight.setTitle(title);
        sight.setName_mark(name_mark);
        sight.setDescription(description);
        if (!Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            sightsImageUpload(sight, file);
        }
        sight.setType_mark(type.getCategory());
        sight.setIcon(type.getIconPath());
        sightsRepository.save(sight);
    }

    public void sightsImageUpload(Sights sight, MultipartFile file) throws IOException {
        File uploadDir = new File("C:/Users/Jlexa/Documents/RussianSights/src/main/resources/uploads");

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();
        file.transferTo(new File(uploadDir + "/" + resultFilename));
        sight.setImage(resultFilename);
    }
}
