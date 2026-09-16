package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

	@GetMapping("/todos")
	public List<Todo> getTodoList() {
		List<Todo> todos = new ArrayList<>();
		todos.add(new Todo(1L, "牛乳を買う"));
		todos.add(new Todo(2L, "レポート提出"));
		return todos;
	}
}
