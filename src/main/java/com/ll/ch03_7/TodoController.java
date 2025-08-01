package com.ll.ch03_7;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private long todosLastIndex;
    private List<Todo> todos;

    public TodoController(){
        todos = new ArrayList<>();
    }

    @GetMapping("")
    public List<Todo> getTodos(){
        return todos;
    }

    @GetMapping("/detail")
    public Todo getTodo(long id){
        return todos
                .stream()
                .filter(
                        todo -> todo.getId() == id
                )
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/{id}")
    public Todo getTodo2(
            @PathVariable long id
    ){
        return todos
                .stream()
                .filter(
                        todo -> todo.getId() == id
                )
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/add")
    public Todo add(String body){
        Todo todo = Todo
                .builder()
                .id(++todosLastIndex)
                .body(body)
                .build();

        todos.add(todo);
        return todo;
    }

    @GetMapping("/remove/{id}")
    public boolean remove(@PathVariable long id){
        boolean removed = todos.removeIf((todo -> todo.getId() == id));

        return removed;
    }

    @GetMapping("/modify/{id}")
    public boolean modify(@PathVariable long id, String body){
        Todo foundTodo = todos
                .stream()
                .filter(
                        t -> t.getId() == id
                )
                .findFirst()
                .orElse(null);

        if(foundTodo == null) return false;

        foundTodo.setBody(body);

        return true;
    }
}
