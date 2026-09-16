package com.example.demo;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@PatchMapping("/todos/{id}")
	public Todo patchTodo(@PathVariable Long id) {
		Todo todo = todoRepository.findById(id);
		if (todo == null) {
			return null;
		}
		todo.setCompleted(!todo.isCompleted());
		return todo;
	}

	@DeleteMapping("/todos/{id}")
	public List<Todo> deleteTodo(@PathVariable Long id) {
		todoRepository.deleteTodo(id);
		return todoRepository.findAll();
	}
}
