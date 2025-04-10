package org.example.tutorial4;

import org.example.tutorial4.repository.AuthorRepository;
import org.example.tutorial4.repository.BookRepository;
import org.example.tutorial4.repository.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class BooksManagerApp implements CommandLineRunner {
	private final AuthorRepository authorRepository;
	private final BookRepository bookRepository;
	private final PublisherRepository publisherRepository;

	public BooksManagerApp(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
		this.publisherRepository = publisherRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BooksManagerApp.class, args);
	}

	@Override
	public void run(String... args) {
		for (var b : bookRepository.findAll()) {
			System.out.println(b);
		}

		for (var a : authorRepository.findAll()) {
			System.out.println(a);
		}

		for (var p : publisherRepository.findAll()) {
			System.out.println(p);
		}

		Scanner in = new Scanner(System.in);
		while(true){
			if (in.nextLine().trim().equals("0")){
				System.exit(0);
			}
		}
	}
}