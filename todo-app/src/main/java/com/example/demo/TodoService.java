package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class TodoService {
	private final TodoRepository todoRepository;

	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	//Todoのリストを返す
	public List<Todo> findAll() {
		return todoRepository.findAll();
	}

	//Todoを新規作成
	public Todo createNewTodo(Todo newTodo) {
		if (newTodo.getTitle() == null || newTodo.getTitle().isEmpty()) {
			return null;
		} else {
			return todoRepository.save(newTodo);
		}
	}

	//Todoの完了を反転する
	public Todo reverseCompletedById(Long id) {
		Optional<Todo> result = todoRepository.findById(id);
		if (result.isEmpty()) {
			return null;
		}
		Todo todo = result.get();
		todo.setCompleted(!todo.isCompleted());
		return todoRepository.save(todo);
	}

	//Todoを削除する
	public List<Todo> deleteTodoById(Long id) {
		Optional<Todo> result = todoRepository.findById(id);
		if (result.isEmpty()) {
			return null;
		}
		todoRepository.deleteById(id);
		return todoRepository.findAll();
	}

}
