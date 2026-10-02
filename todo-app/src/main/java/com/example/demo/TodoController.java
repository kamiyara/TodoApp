package com.example.demo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	private final UserRepository userRepository;

	public TodoController(TodoService todoService, UserRepository userRepository) {
		this.todoService = todoService;
		this.userRepository = userRepository;

	}

	@GetMapping("/todos")
	public ResponseEntity<List<Todo>> getTodoList(@AuthenticationPrincipal UserDetails userDetails) {
		User owner = getCurrentUser(userDetails);
		return ResponseEntity.ok(todoService.findAll(owner));
	}

	@PostMapping("/todos")
	public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo,
			@AuthenticationPrincipal UserDetails userDetails) {
		User owner = getCurrentUser(userDetails);

		Todo createdTodo = todoService.createNewTodo(newTodo, owner);
		if (createdTodo == null) {
			return ResponseEntity.badRequest().build();
		} else {
			return ResponseEntity.ok(createdTodo);
		}
	}

	private User getCurrentUser(UserDetails userDetails) {
		User owner = userRepository.findByUsername(userDetails.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません: "));
		return owner;
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
