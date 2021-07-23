package com.bts.noteapp.contollers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bts.noteapp.dto.ResponseData;
import com.bts.noteapp.dto.TodoData;
import com.bts.noteapp.models.entities.Todo;
import com.bts.noteapp.services.TodoService;

@RestController
@RequestMapping(path = "api/v1/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public Iterable<Todo> findAll() {
        return todoService.findAll();
    }

    @GetMapping("/{id}")
    public Todo findOne(@PathVariable("id") Long id) {
        return todoService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Todo>> create(@Valid @RequestBody TodoData todoData, Errors errors) {
        ResponseData<Todo> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Todo checklist = modelMapper.map(todoData, Todo.class);

        responseData.setStatus(true);
        responseData.getMessage().add("Todo created successfully");
        responseData.setPayload(todoService.save(checklist));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Todo>> update(@PathVariable("id") Long id, @Valid @RequestBody TodoData todoData, Errors errors) {
        ResponseData<Todo> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Todo todo = modelMapper.map(todoData, Todo.class);

        todo.setId(id);

        responseData.setStatus(true);
        responseData.getMessage().add("Todo Updated successfully");
        responseData.setPayload(todoService.save(todo));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    @DeleteMapping("/{id}")
    public void removeOne(@PathVariable("id") Long id) {
        todoService.removeOne(id);
    }

}
