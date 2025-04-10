package com.example.tutorial2.controller;

import com.example.tutorial2.model.Entry;
import com.example.tutorial2.repository.EntryRepository;
import com.example.tutorial2.service.FileService;
import com.example.tutorial2.profile.IProfile;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class FlashcardsController {
    private final EntryRepository repository;
    private final IProfile displayProfile;
    private final Scanner in;
    private final FileService fileService;

    public FlashcardsController(EntryRepository repository, IProfile displayProfile, FileService fileService) {
        this.repository = repository;
        this.displayProfile = displayProfile;
        this.fileService = fileService;
        this.in = new Scanner(System.in);
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Flashcards Menu ---");
            System.out.println("1. Add a new word");
            System.out.println("2. Display all words");
            System.out.println("3. Take a test");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            String option = in.nextLine();

            switch (option) {
                case "1":
                    addNewWord();
                    break;
                case "2":
                    displayAllWords();
                    break;
                case "3":
                    takeTest();
                    break;
                case "0":
                    exit = true;
                    break;
                default:
                    System.out.println("Incorrect input, try again");
            }
        }
    }

    private void addNewWord() {
        System.out.print("Enter Polish word: ");
        String polishVersion = in.nextLine();

        System.out.print("Enter English translation: ");
        String englishVersion = in.nextLine();

        System.out.print("Enter German translation: ");
        String germanVersion = in.nextLine();

        Entry entry = new Entry(polishVersion, englishVersion, germanVersion);
        repository.add(entry);
        fileService.writeEntry(entry);

        System.out.println("Word has been added successfully");
    }

    private void displayAllWords() {
        repository.getAll().clear();
        fileService.loadEntries(fileService.getFileName());
        List<Entry> entries = repository.getAll();

        if (entries.isEmpty()) {
            System.out.println("No flashcards in the dictionary");
            return;
        }

        System.out.println("\n--- Flashcards ---");
        for (Entry entry : entries) {
            System.out.printf("Polish: %s, English: %s, German: %s%n",
                    displayProfile.display(entry.getPolishVersion()),
                    displayProfile.display(entry.getEnglishVersion()),
                    displayProfile.display(entry.getGermanVersion())
            );
        }
    }

    private void takeTest() {
        Entry entry = repository.getRandom();

        if (entry == null) {
            System.out.println("There are no flashcards to take the test");

            return;
        }

        System.out.println("Translate the following word from Polish:");
        System.out.println(displayProfile.display(entry.getPolishVersion()));
        System.out.print("Provide English translation: ");
        String englishInput = in.nextLine();
        System.out.print("Provide German translation: ");
        String germanInput = in.nextLine();

        boolean englishCorrect = entry.getEnglishVersion().equalsIgnoreCase(englishInput.trim());
        boolean germanCorrect = entry.getGermanVersion().equalsIgnoreCase(germanInput.trim());

        if (englishCorrect && germanCorrect) {
            System.out.println("Correct!");
        } else {
            System.out.println("Incorrect. The correct translations are:");
            System.out.println("English: " + entry.getEnglishVersion());
            System.out.println("German: " + entry.getGermanVersion());
        }
    }
}
