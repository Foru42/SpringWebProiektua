package com.ProiektuaCochea.app.domain;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class kotxea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String marka;

	@Column(nullable = false)
	private String modeloa;

	@Column(nullable = false)
	private Integer urtea;

	@Column(nullable = false)
	private Double prezioa;

	@Column(length = 500)
	private String Deskripzioa;

	@Lob
	private byte[] irudia;

	@Transient
	private String irudiaBase64;

	@OneToMany(mappedBy = "kotxea", cascade = CascadeType.ALL)
	private List<Erreserba> erreserbas;

	public kotxea(String marka, String modeloa, Integer urtea, Double prezioa, String Deskripzioa) {
		this.marka = marka;
		this.modeloa = modeloa;
		this.urtea = urtea;
		this.prezioa = prezioa;
		this.Deskripzioa = Deskripzioa;
	}
}
