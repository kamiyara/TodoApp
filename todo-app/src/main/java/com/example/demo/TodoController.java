package com.example.demo;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<List<Todo>> getTodoList() {
		return ResponseEntity.ok(todoRepository.findAll());
	}

	@PostMapping("/todos")
	public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo) {
		return ResponseEntity.ok(todoRepository.save(newTodo));
	}

	@PatchMapping("/todos/{id}")
	public ResponseEntity<Todo> patchTodo(@PathVariable Long id) {
		Todo todo = todoRepository.findById(id);
		if (todo == null) {
			return ResponseEntity.notFound().build();
		}
		todo.setCompleted(!todo.isCompleted());
		return ResponseEntity.ok(todo);
	}

	@DeleteMapping("/todos/{id}")
	public ResponseEntity<List<Todo>> deleteTodo(@PathVariable Long id) {
		Todo todo = todoRepository.findById(id);
		if (todo == null) {
			return ResponseEntity.notFound().build();
		}
		todoRepository.deleteTodo(id);
		return ResponseEntity.ok(todoRepository.findAll());
	}
}
