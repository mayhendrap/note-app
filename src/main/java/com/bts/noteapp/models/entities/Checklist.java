package com.bts.noteapp.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "checklists")
public class Checklist extends BaseEntity<String> {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(nullable = false)
    private String content;

    private boolean checked;

    private LocalDate checkedAt;

    @ManyToOne
    @JoinColumn(name = "todo_id", nullable = false)
    @JsonBackReference
    private Todo todo;

    public Checklist() {
    }

    public Checklist(Long id, String content, boolean checked, LocalDate createdAt, LocalDate checkedAt, Todo todo) {
        this.id = id;
        this.content = content;
        this.checked = checked;
        this.checkedAt = checkedAt;
        this.todo = todo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public LocalDate getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(LocalDate checkedAt) {
        this.checkedAt = checkedAt;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }
}
