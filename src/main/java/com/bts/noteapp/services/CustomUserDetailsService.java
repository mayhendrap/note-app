package com.bts.noteapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bts.noteapp.models.entities.Role;
import com.bts.noteapp.models.entities.UserDAO;
import com.bts.noteapp.models.repos.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		List<SimpleGrantedAuthority> roles = new ArrayList<>();
		
		UserDAO user = userRepo.findByEmail(email);
		
		if (user != null) {
			for (Role role : user.getRoles()) {
				roles.add(new SimpleGrantedAuthority(role.getRole().toString()));
			}
			return new User(user.getEmail(), user.getPassword(), roles);
		}
		
		throw new UsernameNotFoundException("User not found with the email " + email);
		
	}

}
