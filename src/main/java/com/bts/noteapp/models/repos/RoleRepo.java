package com.bts.noteapp.models.repos;

import com.bts.noteapp.models.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepo extends CrudRepository<Role, Long> {
    public Role findByRole(String role);
}
