package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private boolean completed;
	
	@ManyToOne
	private User owner;

	public Todo() {

	}

	public Todo(Long id, String title) {
		this.id = id;
		this.title = title;
		this.completed = false;
	}

	//Getter
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public boolean isCompleted() {
		return completed;
	}
	
	public User getOwner() {
		return owner;
	}

	//Setter
	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public void setOwner(User owner) {
		this.owner=owner;}

}
