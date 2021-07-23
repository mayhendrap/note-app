package com.bts.noteapp.services;

import com.bts.noteapp.models.entities.Role;
import com.bts.noteapp.models.repos.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServices {

    @Autowired
    private RoleRepo roleRepo;

    public Role findByRole(String role) {
        return roleRepo.findByRole(role);
    }

    public Role save(Role role) {
        return roleRepo.save(role);
    }

    public void removeOne(Long id) {
        roleRepo.deleteById(id);
    }
}
