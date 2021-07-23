package com.bts.noteapp.models.repos;

import org.springframework.data.repository.CrudRepository;

import com.bts.noteapp.models.entities.UserDAO;

public interface UserRepo extends CrudRepository<UserDAO, Long> {

    UserDAO findByEmail(String email);
}
