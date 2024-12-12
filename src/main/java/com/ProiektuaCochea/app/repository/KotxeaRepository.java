package com.ProiektuaCochea.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProiektuaCochea.app.domain.kotxea;

@Repository
public interface KotxeaRepository extends JpaRepository<kotxea, Long> {
    

}