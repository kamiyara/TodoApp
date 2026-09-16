package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class TodoRepository {
	private List<Todo> todos = new ArrayList<>();
	private long nextId = 1;

	//全件取得
	public List<Todo> findAll() {
		return todos;

	}

	//新規保存
	public Todo save(Todo todo) {
		todo.setId(nextId);
		nextId++;
		todos.add(todo);
		return todo;
	}

	//IDから検索
	public Todo findById(Long id) {
		for (Todo todo : todos) {
			if (todo.getId().equals(id)) {
				return todo;
			}
		}
		return null;
	}

	//IDから削除
	public List<Todo> deleteTodo(Long id) {
		Todo todo = findById(id);
		if (todo != null) {
			todos.remove(todo);
		}

		return todos;
	}
}
