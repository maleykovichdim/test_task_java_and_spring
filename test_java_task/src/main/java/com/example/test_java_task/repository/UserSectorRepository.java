package com.example.test_java_task.repository;

import com.example.test_java_task.model.pojo.UserSector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSectorRepository extends JpaRepository<UserSector, Long> {
}