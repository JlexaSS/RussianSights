package com.example.JustLearning.repository;

import com.example.JustLearning.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
