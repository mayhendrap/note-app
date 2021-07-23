package com.bts.noteapp.services;

import com.bts.noteapp.models.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bts.noteapp.models.entities.UserDAO;
import com.bts.noteapp.models.repos.UserRepo;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Set<SimpleGrantedAuthority> roles = new HashSet<>();
		UserDAO user = userRepo.findByEmail(email);
		
		if (user != null) {
			for (Role role : user.getRoles()) {
				roles.add(new SimpleGrantedAuthority(role.getRole()));
			}
			return new User(user.getEmail(), user.getPassword(), roles);
		}
		
		throw new UsernameNotFoundException("User not found with the email " + email);
		
	}

}
