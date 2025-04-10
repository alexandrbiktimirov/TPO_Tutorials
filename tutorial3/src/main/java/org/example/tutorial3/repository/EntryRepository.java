package org.example.tutorial3.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.example.tutorial3.entity.Entry;
import org.example.tutorial3.exceptions.FlashcardNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;

@Repository
public class EntryRepository {
    private final EntityManager entityManager;
    private final Random random = new Random();

    public EntryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public int count(){
        Long count = entityManager.createQuery("SELECT COUNT(*) FROM Entry", Long.class).getSingleResult();

        return count.intValue();
    }

    public long generateId() {
        Long id = entityManager.createQuery("SELECT MAX(id) FROM Entry", Long.class).getSingleResult();

        if (id == null) {
            return 1;
        }

        return id + 1;
    }

    @Transactional
    public void add(Entry entry) {
        entry.setId(generateId());
        entityManager.persist(entry);
    }

    public List<Entry> findAll() {
        TypedQuery<Entry> q = entityManager.createQuery("FROM Entry", Entry.class);

        return q.getResultList();
    }

    public Entry findRandom() {
        TypedQuery<Entry> q = entityManager.createQuery("FROM Entry", Entry.class);
        List<Entry> entries = q.getResultList();

        if (entries.isEmpty()) {
            return null;
        }

        return entries.get(random.nextInt(entries.size()));
    }

    public Entry find(String word) throws FlashcardNotFoundException {
        TypedQuery<Entry> q = entityManager.createQuery("FROM Entry WHERE polishVersion = :word OR englishVersion = :word OR germanVersion = :word", Entry.class);
        q.setParameter("word", word);

        if (q.getResultList().isEmpty()) {
            throw new FlashcardNotFoundException();
        }

        return q.getSingleResult();
    }

    public List<Entry> sortWords(String sortingLanguage, int sortingOrder){
        String version;

        if (sortingLanguage.equalsIgnoreCase("English")){
            version = "englishVersion";
        } else if (sortingLanguage.equalsIgnoreCase("German")) {
            version = "germanVersion";
        } else {
            version = "polishVersion";
        }

        String sortQuery = "FROM Entry ORDER BY " + version;
        if (sortingOrder == 1){
            sortQuery += " DESC";
        }

        TypedQuery<Entry> q = entityManager.createQuery(sortQuery, Entry.class);
        return q.getResultList();
    }

    @Transactional
    public void modify(Entry entry, String polishVersion, String englishVersion, String germanVersion){
        Entry mergedEntry = entityManager.merge(entry);

        mergedEntry.setPolishVersion(polishVersion);
        mergedEntry.setEnglishVersion(englishVersion);
        mergedEntry.setGermanVersion(germanVersion);
    }

    @Transactional
    public void delete(Entry entry) {
        Entry mergedEntry = entityManager.merge(entry);

        entityManager.remove(mergedEntry);
    }
}
