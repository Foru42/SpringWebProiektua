package com.ProiektuaCochea.app.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class Direkzioa {
	private String kalea;
	private String herria;
	private String postaKode;

	public Direkzioa(String kalea, String herria, String postaKode) {
		this.kalea = kalea;
		this.herria = herria;
		this.postaKode = postaKode;
	}
}
