package com.ProiektuaCochea.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class GarajeaController {

	@Autowired
	private ErabiltzaileaRepository erabiltzaileaRepository;

	@Autowired
	private GarajeaRepository garajeaRepository;

	@PostMapping("/gorde-garaje")
	public String guardarGaraje(@RequestParam String kalea, @RequestParam String herria, @RequestParam String postaKode,
			Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		erabiltzailea user = erabiltzaileaRepository.findByNombre(username);

		if (user == null) {
			model.addAttribute("errorMessage", "Usuario no encontrado.");
			return "error";
		}

		Direkzioa direkzioa = new Direkzioa(kalea, herria, postaKode);
		Garajea garaje = new Garajea(direkzioa);
		garaje.setErabiltzailea(user);
		user.setGaraje(garaje);

		garajeaRepository.save(garaje);
		erabiltzaileaRepository.save(user);

		return "redirect:/ikusi-kotxeak";
	}

	@PostMapping("/update-garaje")
	public String actualizarGaraje(@RequestParam String kalea, @RequestParam String herria,
			@RequestParam String postaKode, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		erabiltzailea user = erabiltzaileaRepository.findByNombre(username);

		if (user == null) {
			model.addAttribute("errorMessage", "Usuario no encontrado.");
			return "error";
		}

		Direkzioa direkzioa = new Direkzioa(kalea, herria, postaKode);
		user.getGaraje().setDirekzioa(direkzioa);
		garajeaRepository.save(user.getGaraje());

		return "redirect:/ikusi-garajea";
	}

	@GetMapping("/ikusi-garajea")
	public String verGaraje(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		erabiltzailea user = erabiltzaileaRepository.findByNombre(username);

		if (user == null) {
			model.addAttribute("errorMessage", "Errorea: Erabiltzailea ez da aurkitu.");
			return "error";
		}

		if (user.getGaraje() == null) {
			model.addAttribute("errorMessage", "Ez daukazu garajerik erregistratuta. Gehitu bat lehenik.");
			return "garajeaBete";
		}

		Garajea garaje = garajeaRepository.findById(user.getGaraje().getId()).orElse(null);
		if (garaje != null) {
			model.addAttribute("garaje", garaje);
		} else {
			model.addAttribute("errorMessage", "Errorea: Garajea aurkitu ezin da.");
			return "error";
		}

		return "garajeaIkusi";
	}
}