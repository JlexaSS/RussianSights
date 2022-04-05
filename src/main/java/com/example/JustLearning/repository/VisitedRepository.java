package com.example.JustLearning.repository;

import com.example.JustLearning.domain.Sights;
import com.example.JustLearning.domain.User;
import com.example.JustLearning.domain.VisitedSights;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VisitedRepository extends CrudRepository<VisitedSights, Integer> {
    List<VisitedSights> findByUser(User user);
    VisitedSights findByUserAndSight(User user, Sights id);
    List<VisitedSights> findByUserAndVisited(User user, Boolean visited);
    void deleteByUserAndSight(User user, Sights id);

}
