package com.example.JustLearning.repository;

import com.example.JustLearning.domain.Sights;
import com.example.JustLearning.domain.User;
import com.example.JustLearning.domain.VisitedSights;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitedRepository extends JpaRepository<VisitedSights, Integer> {
    VisitedSights findByUserAndSight(User user, Sights id);
    List<VisitedSights> findByUserAndVisited(User user, Boolean visited);
    Boolean existsByUserAndSight(User user, Sights sight);
    void deleteByUserAndSight(User user, Sights id);
}
