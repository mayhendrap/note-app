package com.bts.noteapp.models.repos;

import com.bts.noteapp.models.entities.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistRepo extends JpaRepository<Checklist, Long> {
}
