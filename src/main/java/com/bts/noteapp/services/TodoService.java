package com.bts.noteapp.services;

import com.bts.noteapp.models.entities.Todo;
import com.bts.noteapp.models.repos.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepo todoRepo;

    public Iterable<Todo> findAll() {
        return todoRepo.findAll();
    }

    public Todo findOne(Long id) {
        Optional<Todo> todo = todoRepo.findById(id);
        return todo.get();
    }

    public Todo save(Todo todo) {
        return todoRepo.save(todo);
    }

    public void removeOne(Long id) {
        todoRepo.deleteById(id);
    }
}
