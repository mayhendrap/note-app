package com.bts.noteapp.services;

import com.bts.noteapp.models.entities.Checklist;
import com.bts.noteapp.models.repos.ChecklistRepo;
import com.bts.noteapp.models.repos.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ChecklistService {

    @Autowired
    private ChecklistRepo checklistRepo;

    @Autowired
    private TodoRepo todoRepo;

    public Iterable<Checklist> findAll() {
        return checklistRepo.findAll();
    }

    public Checklist create(Long todoId, Checklist checklist) {
        return todoRepo.findById(todoId).map(t -> {
            checklist.setTodo(t);
            return checklistRepo.save(checklist);
        }).orElseThrow(() -> new RuntimeException("Todo does not exists"));
    }

    public Checklist update(Checklist checklist) {
        return checklistRepo.save(checklist);
    }

    public void removeOne(Long id) {
        checklistRepo.deleteById(id);
    }

}
