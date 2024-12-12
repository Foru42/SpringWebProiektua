package com.ProiektuaCochea.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ProiektuaCochea.app.domain.Direkzioa;
import com.ProiektuaCochea.app.domain.Garajea;
import com.ProiektuaCochea.app.domain.erabiltzailea;
import com.ProiektuaCochea.app.repository.ErabiltzaileaRepository;
import com.ProiektuaCochea.app.repository.GarajeaRepository;

@Controller
public class erregistroController {

    @Autowired
    private ErabiltzaileaRepository erabiltzaileaRepository;

    @Autowired
    private GarajeaRepository garajeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String nombre, 
            @RequestParam String password,
            @RequestParam(required = false) Boolean rol, 
            @RequestParam(required = false) String kalea, 
            @RequestParam(required = false) String herria,
            @RequestParam(required = false) String postaKode, 
            Model model) {

        // Verificar si el usuario ya existe
        if (erabiltzaileaRepository.findByNombre(nombre) != null) {
            model.addAttribute("errorMessage", "Erabiltzailea " + nombre + " Badago erregistratuta, mesedez sartu beste erabiltzaile bat.");
            return "register";
        }

        if (rol == null) {
            rol = false;
        }

        String encodedPassword = passwordEncoder.encode(password);
        erabiltzailea user = new erabiltzailea(nombre, encodedPassword, rol);

        if (kalea != null && !kalea.isEmpty() && herria != null && !herria.isEmpty() && postaKode != null && !postaKode.isEmpty()) {
            Direkzioa direkzioa = new Direkzioa(kalea, herria, postaKode);
            Garajea garaje = new Garajea(direkzioa);
            garaje.setErabiltzailea(user);
            user.setGaraje(garaje);
            garajeRepository.save(garaje);
        }

        erabiltzaileaRepository.save(user);

        return "redirect:/login";
    }
}
