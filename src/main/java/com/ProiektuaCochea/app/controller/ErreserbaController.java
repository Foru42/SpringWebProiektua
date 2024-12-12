package com.ProiektuaCochea.app.controller;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ProiektuaCochea.app.domain.Erreserba;
import com.ProiektuaCochea.app.domain.Garajea;
import com.ProiektuaCochea.app.domain.erabiltzailea;
import com.ProiektuaCochea.app.domain.kotxea;
import com.ProiektuaCochea.app.repository.ErabiltzaileaRepository;
import com.ProiektuaCochea.app.repository.ErreserbaRepository;
import com.ProiektuaCochea.app.repository.GarajeaRepository;
import com.ProiektuaCochea.app.repository.KotxeaRepository;

@Controller
public class ErreserbaController {

	@Autowired
	private KotxeaRepository kotxeaRepository;

	@Autowired
	private ErabiltzaileaRepository erabiltzaileaRepository;

	@Autowired
	private GarajeaRepository garajeaRepository;

	@Autowired
	private ErreserbaRepository erreserbaRepository;

	@GetMapping("/erreserbatu-kotxea/{id}")
	public String reservarCoche(@PathVariable Long id, Model model) {
		kotxea coche = kotxeaRepository.findById(id).orElse(null);

		if (coche == null) {
			model.addAttribute("errorMessage", "Coche no encontrado.");
			return "error";
		}
		if (coche.getIrudia() != null) {
			String base64Image = Base64.getEncoder().encodeToString(coche.getIrudia());
			coche.setIrudiaBase64(base64Image);
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		erabiltzailea user = erabiltzaileaRepository.findByNombre(username);

		if (user.getGaraje() == null) {
			model.addAttribute("errorMessage", "Ez dago garajerik erregistratuta.");
			return "garajeaBete";
		} else {
			Garajea garaje = garajeaRepository.findById(user.getGaraje().getId()).orElse(null);
			if (garaje != null) {
				model.addAttribute("garaje", garaje);
			}
		}

		model.addAttribute("coche", coche);

		model.addAttribute("usuario", user);

		return "erreserbaKonfirma";
	}

	@PostMapping("/confirmar-reserva/{id}")
	public String confirmarReserva(@PathVariable Long id, @RequestParam("fechaReserva") String fechaReserva,
			Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		erabiltzailea user = erabiltzaileaRepository.findByNombre(username);

		if (user == null) {
			model.addAttribute("errorMessage", "Usuario no encontrado.");
			return "error";
		}

		if (user.getGaraje() == null) {
			model.addAttribute("errorMessage", "Ez duzu garajerik.");
			return "garajeaBete";
		}

		kotxea coche = kotxeaRepository.findById(id).orElse(null);

		if (coche == null) {
			model.addAttribute("errorMessage", "Coche no encontrado.");
			return "error";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date fecha = null;
		try {
			fecha = dateFormat.parse(fechaReserva);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Formato de fecha incorrecto.");
			return "error";
		}

		if (erreserbaRepository.existsByErreserbaDataAndKotxea(fecha, coche)) {
			if (coche.getIrudia() != null) {
				String base64Image = Base64.getEncoder().encodeToString(coche.getIrudia());
				coche.setIrudiaBase64(base64Image);
			}
			model.addAttribute("coche", coche);
			model.addAttribute("usuario", user);
			model.addAttribute("errorMessage", "Data " + fecha + " hartuta dago. Mesedez, adierazi beste data bat.");
			return "erreserbaKonfirma";
		}

		Erreserba reserva = new Erreserba(fecha, "Erreserbatuta", user, coche);
		erreserbaRepository.save(reserva);

		return "redirect:/ikusi-kotxeak";
	}

	@GetMapping("/ikusi-erreserba")
	public String showErreserbak(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		erabiltzailea user = erabiltzaileaRepository.findByNombre(username);

		if (user == null) {
			model.addAttribute("errorMessage", "Usuario no encontrado.");
			return "error";
		}

		List<Erreserba> erreserbak = erreserbaRepository.findByErabiltzailea(user);

		for (Erreserba erreserba : erreserbak) {
			kotxea coche = erreserba.getKotxea();
			if (coche.getIrudia() != null) {
				String base64Image = Base64.getEncoder().encodeToString(coche.getIrudia());
				coche.setIrudiaBase64(base64Image);
			}
		}

		model.addAttribute("erreserbak", erreserbak);

		return "erreserbaLista";
	}

	@GetMapping("/eliminar-Erreserba/{id}")
	public String deleteCar(@PathVariable("id") Long id) {
		Erreserba erreserbak = erreserbaRepository.findById(id).orElse(null);
		if (erreserbak != null) {
			erreserbaRepository.delete(erreserbak);
		}
		return "redirect:/ikusi-erreserba";
	}

}
