package com.example.testtravelfactory.dao;

import com.example.testtravelfactory.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
