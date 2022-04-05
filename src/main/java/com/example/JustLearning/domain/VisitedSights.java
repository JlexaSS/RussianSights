package com.example.JustLearning.domain;

import javax.persistence.*;

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

    public Sights getSight() {
        return sight;
    }

    public void setSight(Sights sight) {
        this.sight = sight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }
}
