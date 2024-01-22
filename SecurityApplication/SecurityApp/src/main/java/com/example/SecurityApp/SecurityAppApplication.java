package com.example.SecurityApp;

import com.example.SecurityApp.model.Role;
import com.example.SecurityApp.model.User;
import com.example.SecurityApp.request.UserRegistrationRequest;
import com.example.SecurityApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SecurityAppApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(SecurityAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UserRegistrationRequest user = new UserRegistrationRequest();
		user.setUsername("victoria@mail.com");
		user.setPassword(passwordEncoder.encode("password"));
		user.setRole(Role.ADMIN);

		userService.saveUser(user);
	}
}
