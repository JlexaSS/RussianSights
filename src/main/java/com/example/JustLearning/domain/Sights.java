package com.example.JustLearning.domain;

import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Sights {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private Point geom;
    private String type_mark;
    private String name_mark;
    private Float rating;
    private String description;
    private String icon;
    private String image;
}
