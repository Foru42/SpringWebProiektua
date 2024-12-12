package com.ProiektuaCochea.app.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Garajea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private Direkzioa direkzioa;

	@OneToOne(mappedBy = "garaje", cascade = CascadeType.ALL)
	private erabiltzailea erabiltzailea;

	public Garajea(Direkzioa direkzioa) {
		this.direkzioa = direkzioa;
	}

}
