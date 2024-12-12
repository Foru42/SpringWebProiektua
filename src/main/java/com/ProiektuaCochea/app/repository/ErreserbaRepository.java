package com.ProiektuaCochea.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProiektuaCochea.app.domain.Erreserba;
import com.ProiektuaCochea.app.domain.erabiltzailea;
import com.ProiektuaCochea.app.domain.kotxea;

public interface ErreserbaRepository extends JpaRepository<Erreserba, Long> {
    
    boolean existsByErreserbaDataAndKotxea(Date erreserbaData, kotxea coche);
    List<Erreserba> findByErabiltzailea(erabiltzailea user);
}
