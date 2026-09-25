package com.example.demo;

import java.util.List;

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
		Todo todo = todoRepository.findById(id);
		if (!(todo == null)) {
			todo.setCompleted(!todo.isCompleted());
		}
		return todo;
	}

	//Todoを削除する
	public List<Todo> deleteTodoById(Long id) {
		Todo todo = todoRepository.findById(id);
		if (!(todo == null)) {
			return todoRepository.deleteTodo(id);
		} else {
			return null;
		}
	}

}
