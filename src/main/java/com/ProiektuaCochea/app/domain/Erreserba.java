package com.ProiektuaCochea.app.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Erreserba {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date erreserbaData;

	@Column(nullable = false)
	private String egoera;

	@ManyToOne
	@JoinColumn(name = "erabiltzailea_id", nullable = false)
	private erabiltzailea erabiltzailea;

	@ManyToOne
	@JoinColumn(name = "kotxea_id", nullable = false)
	private kotxea kotxea;


	public Erreserba(Date erreserbaData, String egoera, erabiltzailea erabiltzailea, kotxea kotxea) {
		this.erreserbaData = erreserbaData;
		this.egoera = egoera;
		this.erabiltzailea = erabiltzailea;
		this.kotxea = kotxea;
	}
}
