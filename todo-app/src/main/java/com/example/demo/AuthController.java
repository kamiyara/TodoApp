package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;

	}

	@PostMapping("/register")
	public ResponseEntity<String> createNewUser(@RequestBody User newUser) {
		String newUsername = newUser.getUsername();

		//Usernameが空またはnullの場合は400を返す
		if (newUsername == null || newUsername.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		//Usernameが既に使われている場合は400を返す
		if (userRepository.findByUsername(newUsername).isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		//Passwordのハッシュ化
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

		//新規のUserとして保存
		userRepository.save(newUser);

		//Userの新規作成成功時は200と新規作成されたUserを返す
		return ResponseEntity.ok("登録できました。");
	}
}
