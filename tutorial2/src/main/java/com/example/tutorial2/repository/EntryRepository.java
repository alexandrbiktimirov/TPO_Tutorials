package com.example.tutorial2.repository;

import com.example.tutorial2.model.Entry;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;

@Repository
public class EntryRepository {
    private final List<Entry> entries;
    private final Random random = new Random();

    public EntryRepository(List<Entry> entries) {
        this.entries = entries;
    }

    public void add(Entry entry) {
        entries.add(entry);
    }

    public List<Entry> getAll() {
        return entries;
    }

    public Entry getRandom() {
        if (entries.isEmpty()) {
            return null;
        }

        return entries.get(random.nextInt(entries.size()));
    }
}
