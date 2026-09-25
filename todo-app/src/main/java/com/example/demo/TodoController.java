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

	private final TodoService todoService;

	public TodoController(TodoService todoService) {
		this.todoService = todoService;

	}

	@GetMapping("/todos")
	public ResponseEntity<List<Todo>> getTodoList() {
		return ResponseEntity.ok(todoService.findAll());
	}

	@PostMapping("/todos")
	public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo) {
		Todo createdTodo = todoService.createNewTodo(newTodo);
		if (createdTodo == null) {
			return ResponseEntity.badRequest().build();
		} else {
			return ResponseEntity.ok(createdTodo);
		}
	}

	@PatchMapping("/todos/{id}")
	public ResponseEntity<Todo> patchTodo(@PathVariable Long id) {
		Todo todo = todoService.reverseCompletedById(id);
		if (todo == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(todo);
		}
	}

	@DeleteMapping("/todos/{id}")
	public ResponseEntity<List<Todo>> deleteTodo(@PathVariable Long id) {
		List<Todo> todos = todoService.deleteTodoById(id);
		if (todos == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(todos);
		}
	}
}
