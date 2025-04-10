package org.example.tutorial3_2;

import org.example.tutorial3_2.controller.FlashcardsController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlashcardsApp implements CommandLineRunner {
	private final FlashcardsController flashcardsController;

	public FlashcardsApp(FlashcardsController flashcardsController) {
		this.flashcardsController = flashcardsController;
	}

	public static void main(String[] args) {
		SpringApplication.run(FlashcardsApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		flashcardsController.start();
	}
}
