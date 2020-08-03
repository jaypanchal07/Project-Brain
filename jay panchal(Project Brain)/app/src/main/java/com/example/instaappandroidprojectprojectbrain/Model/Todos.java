package com.example.instaappandroidprojectprojectbrain.Model;


import java.util.List;

public class Todos {

    private Idea todoIdea;

    public Todos() {}

    public Todos(Idea todoIdea) {

        this.todoIdea = todoIdea;
    }

    public Idea getTodoIdea() {
        return todoIdea;
    }

    public void setTodoIdea(Idea todoIdea) {
        this.todoIdea = todoIdea;
    }
}
