package com.bts.noteapp.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "todos")
public class Todo extends BaseEntity<String> {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "todo")
    @JsonManagedReference
    private Set<Checklist> checklists;

    public Todo() {
    }

    public Todo(Long id, String name, LocalDate createdAt, Set<Checklist> checklists) {
        this.id = id;
        this.name = name;
        this.checklists = checklists;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Checklist> getChecklists() {
        return checklists;
    }

    public void setChecklists(Set<Checklist> checklists) {
        this.checklists = checklists;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", checklists=" + checklists +
                '}';
    }
}
