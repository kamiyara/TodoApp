package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private TodoRepository todoRepository;

	@BeforeEach
	void resetData() {
		todoRepository.deleteAll();
	}

	@Test
	void 一覧取得は200を返す() throws Exception {
		mockMvc.perform(get("/todos"))
				.andExpect(status().isOk());
	}

	@Test
	void ID作成は200を返す() throws Exception {
		mockMvc.perform(post("/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"title\":\"テスト用Todo\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("テスト用Todo"))
				.andExpect(jsonPath("$.completed").value(false));
	}

	@Test
	void ID作成時タイトルが空の場合は400を返す() throws Exception {
		mockMvc.perform(post("/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"title\":\"\"}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void ID作成時タイトルがnullの場合は400を返す() throws Exception {
		mockMvc.perform(post("/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void IDの完了切り替えは要素があれば200を返す() throws Exception {
		String response = mockMvc.perform(post("/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"title\":\"テスト用Todo\"}"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		Todo createdTodo = new ObjectMapper().readValue(response, Todo.class);

		mockMvc.perform(patch("/todos/" + createdTodo.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.completed").value(true));
	}

	@Test
	void IDの完了切り替えは要素が無ければ404を返す() throws Exception {
		mockMvc.perform(patch("/todos/999"))
				.andExpect(status().isNotFound());
	}

	@Test
	void ID削除は要素があれば200を返す() throws Exception {
		String response1 = mockMvc.perform(post("/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"title\":\"削除されるTodo\"}"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		Todo createdTodo1 = new ObjectMapper().readValue(response1, Todo.class);

		String response2 = mockMvc.perform(post("/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"title\":\"削除されずに残るTodo\"}"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		Todo createdTodo2 = new ObjectMapper().readValue(response2, Todo.class);

		mockMvc.perform(delete("/todos/" + createdTodo1.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].title").value("削除されずに残るTodo"));
	}

	@Test
	void ID削除は要素が無ければ404を返す() throws Exception {
		mockMvc.perform(delete("/todos/999"))
				.andExpect(status().isNotFound());
	}

}
