package com.example.demo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

	private final TodoRepository todoRepository;

	public TodoController(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	@GetMapping("/todos")
	public List<Todo> getTodoList() {
		return todoRepository.findAll();
	}

	@PostMapping("/todos")
	public Todo createTodo(@RequestBody Todo newTodo) {
		return todoRepository.save(newTodo);
	}
}
