package org.example.tutorial3_2.service;

import jakarta.transaction.Transactional;
import org.example.tutorial3_2.exceptions.DuplicateFoundException;
import org.example.tutorial3_2.exceptions.FlashcardNotFoundException;
import org.example.tutorial3_2.entity.Entry;
import org.example.tutorial3_2.repository.EntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class FileService {
    private final EntryRepository entryRepository;

    public FileService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @Transactional
    public void init() {
        if (entryRepository.count() == 0) {
            try{
                writeEntry(new Entry("pies", "dog", "Hund"));
                writeEntry(new Entry("szkoła", "school", "Schule"));
                writeEntry(new Entry("samochód", "car", "Auto"));
            } catch(DuplicateFoundException e) {
                System.out.println("Duplicates are not allowed");
            }
        }
    }

    public long generateId() {
        if(entryRepository.count() == 0) {
            return 1;
        }

        long id = entryRepository.findTopByOrderByIdDesc().getId();

        return id + 1;
    }

    @Transactional
    public void writeEntry(Entry entry) throws DuplicateFoundException {
        for (Entry e : entryRepository.findAll()){
            if (entry.equals(e)){
                throw new DuplicateFoundException();
            }
        }

        entry.setId(generateId());
        entryRepository.save(entry);
    }

    public List<Entry> loadEntries() {
        return entryRepository.findAll();
    }

    public Entry getRandom(){
        List<Entry> entryList = entryRepository.findAll();

        if (entryList.isEmpty()) {
            return null;
        }

        Random random = new Random();

        return entryList.get(random.nextInt(entryList.size()));
    }

    public Entry searchFlashcard(String word) throws FlashcardNotFoundException {
        Entry result = entryRepository.findByPolishVersionOrEnglishVersionOrGermanVersion(word, word, word);

        if (result == null) {
            throw new FlashcardNotFoundException();
        }

        return result;
    }

    public List<Entry> sortWords(String sortingLanguage, int sortingOrder) {
        if (sortingLanguage.equalsIgnoreCase("English")) {
            return sortingOrder == 0 ? entryRepository.findByOrderByEnglishVersionAsc() : entryRepository.findByOrderByEnglishVersionDesc();
        } else if (sortingLanguage.equalsIgnoreCase("German")) {
            return sortingOrder == 0 ? entryRepository.findByOrderByGermanVersionAsc() : entryRepository.findByOrderByGermanVersionDesc();
        } else {
            return sortingOrder == 0 ? entryRepository.findByOrderByPolishVersionAsc() : entryRepository.findByOrderByPolishVersionDesc();
        }
    }

    public void deleteFlashcard(Entry entry) {
        entryRepository.delete(entry);
    }

    public void modifyFlashcard(Entry entry, String polishVersion, String englishVersion, String germanVersion) {
        entry.setPolishVersion(polishVersion);
        entry.setEnglishVersion(englishVersion);
        entry.setGermanVersion(germanVersion);

        entryRepository.save(entry);
    }
}