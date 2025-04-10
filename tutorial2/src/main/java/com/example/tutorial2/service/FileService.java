package com.example.tutorial2.service;

import com.example.tutorial2.model.Entry;
import com.example.tutorial2.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileService {
    private EntryRepository entryRepository;
    private String fileName;

    public FileService(EntryRepository entryRepository, @Value("${pl.edu.pja.tpo02.filename}") String fileName) {
        this.entryRepository = entryRepository;
        this.fileName = fileName;
    }

    public void loadEntries(String filename) {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = in.readLine()) != null) {
                if(line.trim().isEmpty()){
                    continue;
                }

                String[] parts = line.split(";");

                if(parts.length == 3) {
                    Entry entry = new Entry(parts[0].trim(), parts[1].trim(), parts[2].trim());
                    entryRepository.add(entry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeEntry(Entry entry) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true))) {
            out.write(entry.getPolishVersion() + ";"
                    + entry.getEnglishVersion() + ";"
                    + entry.getGermanVersion());
            out.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }
}
