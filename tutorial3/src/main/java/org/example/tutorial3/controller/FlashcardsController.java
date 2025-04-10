package org.example.tutorial3.controller;

import org.example.tutorial3.exceptions.DuplicateFoundException;
import org.example.tutorial3.exceptions.FlashcardNotFoundException;
import org.example.tutorial3.entity.Entry;
import org.example.tutorial3.service.FileService;
import org.example.tutorial3.profile.IProfile;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class FlashcardsController {
    private final IProfile displayProfile;
    private final Scanner in;
    private final FileService fileService;

    public FlashcardsController(IProfile displayProfile, FileService fileService) {
        this.displayProfile = displayProfile;
        this.fileService = fileService;
        this.in = new Scanner(System.in);
    }

    public void start() {
        fileService.init();
        while (true) {
            System.out.println("\n--- Flashcards Menu ---");
            System.out.println("1. Add a new word");
            System.out.println("2. Display all words");
            System.out.println("3. Take a test");
            System.out.println("4. Find a flashcard");
            System.out.println("5. Sort flashcards");
            System.out.println("6. Delete a flashcard");
            System.out.println("7. Modify a flashcard");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            String option = in.nextLine();

            switch (option) {
                case "1" -> addNewWord();
                case "2" -> displayAllWords();
                case "3" -> takeTest();
                case "4" -> findFlashcard();
                case "5" -> sortFlashcards();
                case "6" -> deleteFlashcard();
                case "7" -> modifyFlashcard();
                case "0" -> System.exit(0);
                default -> System.out.println("Incorrect input, try again");
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
        try{
            fileService.writeEntry(entry);
        } catch (DuplicateFoundException e){
            System.out.println("Duplicates are not allowed");
            return;
        }

        System.out.println("Word has been added successfully");
    }

    private void displayAllWords() {
        List<Entry> entries = fileService.loadEntries();

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
        Entry entry = fileService.getRandom();

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

    private void findFlashcard(){
        System.out.println("Please write a word you want to find: ");
        String word = in.nextLine();

        Entry entry;
        try{
            entry = fileService.searchFlashcard(word);
        } catch(FlashcardNotFoundException e){
            System.out.println("Word not found");
            return;
        }

        System.out.printf("Polish: %s, English: %s, German: %s%n",
                displayProfile.display(entry.getPolishVersion()),
                displayProfile.display(entry.getEnglishVersion()),
                displayProfile.display(entry.getGermanVersion()));
    }

    private void sortFlashcards(){
        System.out.println("In which language do you want to sort the words?");
        String sortingLanguage = in.nextLine();

        System.out.println("In which order do you want to sort the words? Ascending (0) or Descending order (1)?");
        int sortingOrder;
        try{
            sortingOrder = Integer.parseInt(in.nextLine());
        } catch(NumberFormatException e){
            System.out.println("Incorrect input. Please type 0 or 1");
            return;
        }

        List<Entry> entryList = fileService.sortWords(sortingLanguage, sortingOrder);

        for(Entry entry : entryList){
            System.out.printf("Polish: %s, English: %s, German: %s%n",
                    displayProfile.display(entry.getPolishVersion()),
                    displayProfile.display(entry.getEnglishVersion()),
                    displayProfile.display(entry.getGermanVersion())
            );
        }
    }

    private void deleteFlashcard() {
        System.out.println("Type in the word you want to delete: ");
        String word = in.nextLine();

        Entry entry;
        try{
            entry = fileService.searchFlashcard(word);
        } catch(FlashcardNotFoundException e){
            System.out.println("Word not found");
            return;
        }

        fileService.deleteFlashcard(entry);
        System.out.println("Word has been successfully deleted");
    }

    private void modifyFlashcard(){
        System.out.println("Type in the flashcard you want to modify: ");
        String word = in.nextLine();

        Entry entry;
        try{
            entry = fileService.searchFlashcard(word);
        } catch(FlashcardNotFoundException e){
            System.out.println("Flashcard not found");
            return;
        }

        System.out.print("Enter new Polish word: ");
        String polishVersion = in.nextLine();

        System.out.print("Enter new English translation: ");
        String englishVersion = in.nextLine();

        System.out.print("Enter new German translation: ");
        String germanVersion = in.nextLine();

        fileService.modifyFlashcard(entry, polishVersion, englishVersion, germanVersion);
        System.out.println("Flashcard has been modified");
    }
}