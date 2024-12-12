package com.ProiektuaCochea.app.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Gidaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreConductor;

    @Column(nullable = false)
    private String telefonoConductor;

    // Nueva relación ManyToMany con erabiltzailea
    @ManyToMany(mappedBy = "gidariak")
    private Set<erabiltzailea> erabiltzaileak = new HashSet<>();

    public Gidaria(String nombreConductor, String telefonoConductor) {
        this.nombreConductor = nombreConductor;
        this.telefonoConductor = telefonoConductor;
    }
}
