package com.ProiektuaCochea.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ProiektuaCochea.app.domain.erabiltzailea;
import com.ProiektuaCochea.app.repository.ErabiltzaileaRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private ErabiltzaileaRepository erabiltzaileaRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		erabiltzailea user = erabiltzaileaRepository.findByNombre(username);

		if (user == null) {
			throw new UsernameNotFoundException("Erabiltzailea ez da aurkitu");
		}

		return User.builder().username(user.getNombre()).password(user.getPassword()) 
				.roles(user.getRol() ? "ADMIN" : "USER").build();
	}
}
