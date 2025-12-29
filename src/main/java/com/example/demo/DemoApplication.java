package com.example.demo;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/")
	public Map<String, Object> info() {
		return Map.of(
			"service", "springboot-java",
			"message", "Spring Boot service deployed via a GitOps CI/CD pipeline",
			"status", "running",
			"timestamp", LocalDateTime.now().toString()
		);
	}

	@GetMapping("/health")
	public String health() {
		return "OK";
	}
}
