package com.example.JustLearning.repository;

import com.example.JustLearning.domain.Sights;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SightsRepository extends CrudRepository<Sights, Integer> {
    @Query(value = "select 1 as id, datatojson() as jsos", nativeQuery = true)
    String findNear();
}
