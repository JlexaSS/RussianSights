package com.example.JustLearning.enums;

import lombok.Getter;

@Getter
public enum markTypes {
    MUSEUM("Музей", "fe12e71b-94d5-40f6-a3fc-0c00af30fac2.museum.png"),
    MONUMENT("Памятник", "fe12e71b-94d5-40f6-a3fc-0c00af30fac5.monument.png"),
    CHURCH("Храм","fe12e71b-94d5-40f6-a3fc-0c00af30fac1.church.png"),
    NATURE("Природа", "fe12e71b-94d5-40f6-a3fc-0c00af30fac3.nature.png"),
    SCULPTURE("Скульптура", "fe12e71b-94d5-40f6-a3fc-0c00af30fac4.sculpture.png"),
    CULTURE("Культурное наследие", "fe12e71b-94d5-40f6-a3fc-0c00af30fa44.cult.png");
    private String category;
    private String iconPath;

    markTypes(String category, String iconPath) {
        this.category = category;
        this.iconPath = iconPath;
    }
}
