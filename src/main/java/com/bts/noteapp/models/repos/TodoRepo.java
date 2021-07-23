package com.bts.noteapp.models.repos;

import com.bts.noteapp.models.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepo extends JpaRepository<Todo, Long> {
}
