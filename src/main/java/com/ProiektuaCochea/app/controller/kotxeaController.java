package com.ProiektuaCochea.app.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ProiektuaCochea.app.domain.kotxea;
import com.ProiektuaCochea.app.repository.KotxeaRepository;

@Controller
public class kotxeaController {

	@Autowired
	private KotxeaRepository kotxeaRepository;

	@RequestMapping("/gehi-kotxea")
	public String showForm() {
		return "KotxeaGehitu";
	}

	@PostMapping("/gorde-kotxea")
	public String saveVehicle(@RequestParam String marka, @RequestParam String modeloa, @RequestParam Integer urtea,
			@RequestParam Double prezioa,@RequestParam String Deskripzioa, @RequestParam(required = false) MultipartFile irudia,
			 Model model) throws IOException {

		kotxea nuevoVehiculo = new kotxea(marka, modeloa, urtea, prezioa, Deskripzioa);

		if (irudia != null && !irudia.isEmpty()) {
			nuevoVehiculo.setIrudia(irudia.getBytes());
		}

		kotxeaRepository.save(nuevoVehiculo);
		model.addAttribute("message", "Vehículo agregado con éxito.");
		return "redirect:/ikusi-kotxeak";
	}

	@GetMapping("/ikusi-kotxeak")
	public String showVehicleList(Model model) {
		List<kotxea> coches = kotxeaRepository.findAll();

		for (kotxea coche : coches) {
			if (coche.getIrudia() != null) {
				String base64Image = Base64.getEncoder().encodeToString(coche.getIrudia());
				coche.setIrudiaBase64(base64Image);
			}
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String rol = auth.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse("USER");

		System.out.println("Rol obtenido: " + rol);

		model.addAttribute("coches", coches);
		model.addAttribute("rol", rol);

		return "PantailaPrintzipala";
	}

	@GetMapping("/editar-coche/{id}")
	public String editCar(@PathVariable("id") Long id, Model model) {
		kotxea coche = kotxeaRepository.findById(id).orElse(null);
		if (coche != null) {
			model.addAttribute("coche", coche);
			return "kotxeaEditatu";
		} else {
			return "redirect:/ikusi-kotxeak";
		}
	}

	@GetMapping("/eliminar-coche/{id}")
	public String deleteCar(@PathVariable("id") Long id) {
		kotxea coche = kotxeaRepository.findById(id).orElse(null);
		if (coche != null) {
			kotxeaRepository.delete(coche);
		}
		return "redirect:/ikusi-kotxeak";
	}

	@PostMapping("/actualizar-kotxea")
	public String updateCar(@RequestParam Long id, @RequestParam String marka, @RequestParam String modeloa,
			@RequestParam Integer urtea, @RequestParam Double prezioa,
			@RequestParam(required = false) MultipartFile irudia, @RequestParam String Deskripzioa, Model model)
			throws IOException {

		kotxea coche = kotxeaRepository.findById(id).orElse(null);

		if (coche != null) {
			coche.setMarka(marka);
			coche.setModeloa(modeloa);
			coche.setUrtea(urtea);
			coche.setPrezioa(prezioa);
			coche.setDeskripzioa(Deskripzioa);

			if (irudia != null && !irudia.isEmpty()) {
				coche.setIrudia(irudia.getBytes());
			}

			kotxeaRepository.save(coche);
			model.addAttribute("message", "Kotxea aktualizatua.");
			return "redirect:/ikusi-kotxeak";
		}

		model.addAttribute("message", "kotxea ez topatua.");
		return "redirect:/ikusi-kotxeak";
	}

	@GetMapping("/ikusi-gehiagoKotxea/{id}")
	public String KotxeaGehiagoIkusi(@PathVariable("id") Long id, Model model) {
		kotxea coche = kotxeaRepository.findById(id).orElse(null);
		if (coche != null) {
			String base64Image = Base64.getEncoder().encodeToString(coche.getIrudia());
			coche.setIrudiaBase64(base64Image);
			model.addAttribute("coche", coche);
			return "kotxeaIkusiGehiago";
		} else {
			return "redirect:/ikusi-kotxeak";
		}
	}
}
