package com.example.JustLearning.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class VisitedSights {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "sight_id")
    private Sights sight;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private Float rating;
    private Boolean visited;

    public VisitedSights(){}

    public VisitedSights(Sights sight, User user, Float rating){
        this.sight = sight;
        this.user = user;
        this.rating = (float)0.0;
        this.visited = false;
    }
}
