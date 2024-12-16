package com.ProiektuaCochea.app.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class erabiltzailea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private Boolean rol;

	@ElementCollection
	private List<String> telefonoak;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "garaje_id")
	private Garajea garaje;

	public erabiltzailea(String nombre, String password, Boolean rol) {
		this.nombre = nombre;
		this.password = password;
		this.rol = rol;
	}
	


}
