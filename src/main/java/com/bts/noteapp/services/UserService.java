package com.bts.noteapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bts.noteapp.models.entities.UserDAO;
import com.bts.noteapp.models.repos.UserRepo;

@Service
public class UserService {
	
	@Autowired
    private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder bcryptPasswordEncoder;

	public UserDAO registerUser(UserDAO user) {
        UserDAO userExists = userRepo.findByEmail(user.getEmail());

        if (userExists != null) {
            throw new RuntimeException(
                    String.format("User with emaiil %s already exists", user.getEmail())
            );
        }

        String encodedPassword = bcryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepo.save(user);
    }
}
