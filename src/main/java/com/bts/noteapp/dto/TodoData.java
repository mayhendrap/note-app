package com.bts.noteapp.dto;

import javax.validation.constraints.NotEmpty;

public class TodoData {

    @NotEmpty(message = "Todo name is required")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
