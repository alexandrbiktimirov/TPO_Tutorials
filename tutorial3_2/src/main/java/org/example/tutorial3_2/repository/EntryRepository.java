package org.example.tutorial3_2.repository;

import org.example.tutorial3_2.entity.Entry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends CrudRepository<Entry, Long>{
    Entry findByPolishVersionOrEnglishVersionOrGermanVersion(String polishVersion, String englishVersion, String germanVersion);

    List<Entry> findAll();

    Entry findTopByOrderByIdDesc();

    List<Entry> findByOrderByPolishVersionAsc();
    List<Entry> findByOrderByPolishVersionDesc();
    List<Entry> findByOrderByEnglishVersionAsc();
    List<Entry> findByOrderByEnglishVersionDesc();
    List<Entry> findByOrderByGermanVersionAsc();
    List<Entry> findByOrderByGermanVersionDesc();
}