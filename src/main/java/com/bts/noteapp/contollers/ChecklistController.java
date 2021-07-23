package com.bts.noteapp.contollers;

import com.bts.noteapp.dto.ChecklistData;
import com.bts.noteapp.dto.ResponseData;
import com.bts.noteapp.models.entities.Checklist;
import com.bts.noteapp.services.ChecklistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/todo")
public class ChecklistController {

    @Autowired
    private ChecklistService checklistService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("checklists")
    public Iterable<Checklist> findAll() {
    	System.out.println("Test");
        return checklistService.findAll();
    }

    @PostMapping("/{todoId}/checklist")
    public ResponseEntity<ResponseData<Checklist>> create(@Valid @RequestBody ChecklistData checklistData, @PathVariable("todoId") Long todoId, Errors errors) {

        ResponseData<Checklist> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Checklist checklist = modelMapper.map(checklistData, Checklist.class);

        responseData.setStatus(true);
        responseData.getMessage().add("Checklist created successfully");
        responseData.setPayload(checklistService.create(todoId, checklist));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    @PutMapping("/{todoId}/checklist/{checklistId}")
    public ResponseEntity<ResponseData<Checklist>> update(@PathVariable("todoId") Long todoId, @PathVariable("checklistId") Long checklistId, @Valid @RequestBody ChecklistData checklistData, Errors errors) {
        ResponseData<Checklist> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Checklist checklist = modelMapper.map(checklistData, Checklist.class);

        checklist.setId(checklistId);

        responseData.setStatus(true);
        responseData.getMessage().add("Checklist updated successfully");
        responseData.setPayload(checklistService.update(checklist));
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @DeleteMapping("/{id}/checklist/{checklistId}")
    public void removeOne(@PathVariable("checklistId") Long checklistId) {
        checklistService.removeOne(checklistId);
    }
}
