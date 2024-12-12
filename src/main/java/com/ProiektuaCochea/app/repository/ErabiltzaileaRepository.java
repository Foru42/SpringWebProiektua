package com.ProiektuaCochea.app.repository;

import com.ProiektuaCochea.app.domain.erabiltzailea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErabiltzaileaRepository extends JpaRepository<erabiltzailea, Long> {
	 erabiltzailea findByNombre(String nombre);
	 
	 erabiltzailea findByNombreAndPassword(String nombre, String password);
}
