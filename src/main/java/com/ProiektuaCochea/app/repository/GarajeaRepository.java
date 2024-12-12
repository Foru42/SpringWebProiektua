package com.ProiektuaCochea.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProiektuaCochea.app.domain.Garajea;
import com.ProiektuaCochea.app.domain.erabiltzailea;

public interface GarajeaRepository extends JpaRepository<Garajea, Long> {
	
}
