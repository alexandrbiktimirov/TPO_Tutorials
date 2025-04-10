package org.example.tutorial3.service;

import jakarta.transaction.Transactional;
import org.example.tutorial3.entity.Entry;
import org.example.tutorial3.exceptions.DuplicateFoundException;
import org.example.tutorial3.exceptions.FlashcardNotFoundException;
import org.example.tutorial3.repository.EntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final EntryRepository entryRepository;

    public FileService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @Transactional
    public void init() {
        if (entryRepository.count() == 0) {
            entryRepository.add(new Entry("pies", "dog", "Hund"));
            entryRepository.add(new Entry("szkoła", "school", "Schule"));
            entryRepository.add(new Entry("samochód", "car", "Auto"));
        }
    }

    public List<Entry> loadEntries() {
        return entryRepository.findAll();
    }

    public void writeEntry(Entry entry) throws DuplicateFoundException {
        for (Entry e : entryRepository.findAll()){
            if (entry.equals(e)){
                throw new DuplicateFoundException();
            }
        }

        entryRepository.add(entry);
    }

    public Entry getRandom(){
        return entryRepository.findRandom();
    }

    public Entry searchFlashcard(String word) throws FlashcardNotFoundException {
        return entryRepository.find(word);
    }

    public List<Entry> sortWords(String sortingLanguage, int sortingOrder) {
        return entryRepository.sortWords(sortingLanguage, sortingOrder);
    }

    public void deleteFlashcard(Entry entry) {
        entryRepository.delete(entry);
    }

    public void modifyFlashcard(Entry entry, String polishVersion, String englishVersion, String germanVersion) {
        entryRepository.modify(entry, polishVersion, englishVersion, germanVersion);
    }
}
