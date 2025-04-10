package org.example.tutorial4_2;

import org.example.tutorial4_2.controller.BlogController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApp implements CommandLineRunner {
	private final BlogController blogController;

	public BlogApp(BlogController blogController) {
		this.blogController = blogController;
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApp.class, args);
	}

	@Override
	public void run(String... args) {
		blogController.start();
	}
}
