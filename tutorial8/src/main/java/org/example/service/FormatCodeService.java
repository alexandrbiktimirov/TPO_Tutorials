package org.example.service;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.example.exception.CodeNotFoundException;
import org.example.model.CodeSnippet;
import org.example.model.SourceCodeDTO;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;

@Service
public class FormatCodeService {
    private final Formatter formatter;

    public FormatCodeService(Formatter formatter) {
        this.formatter = formatter;
        startCleanerThread();
    }

    public void saveAndScheduleDeletion(SourceCodeDTO dto) throws FormatterException, IOException {
        String original = dto.getCode();
        int duration = dto.getDuration();
        String id = dto.getId();

        String formatted = formatter.formatSource(original);
        CodeSnippet snippet = new CodeSnippet(original, formatted, duration);
        File directory = new File("data");
        directory.mkdirs();

        File out = new File(directory, "code_" + id + ".ser");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(out))) {
            objectOutputStream.writeObject(snippet);
        }
    }

    public String loadFormattedCode(String id) throws IOException, ClassNotFoundException, CodeNotFoundException {
        CodeSnippet snippet = readSnippet(id);
        return snippet.getFormattedCode();
    }

    public String loadOriginalCode(String id) throws IOException, ClassNotFoundException, CodeNotFoundException {
        CodeSnippet snippet = readSnippet(id);
        return snippet.getOriginalCode();
    }

    private CodeSnippet readSnippet(String id) throws IOException, ClassNotFoundException, CodeNotFoundException {
        File file = new File("data/code_" + id + ".ser");

        if (!file.exists()) {
            throw new CodeNotFoundException("No code with ID: " + id);
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            return (CodeSnippet) objectInputStream.readObject();
        }
    }

    public void startCleanerThread() {
        Thread cleaner = new Thread(() -> {
            while (true) {
                deleteExpiredFiles();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "FormatCleaner");

        cleaner.setDaemon(true);
        cleaner.start();
    }

    private void deleteExpiredFiles() {
        File directory = new File("data");
        File[] files = directory.listFiles((_, name) -> name.endsWith(".ser"));
        if (files == null || files.length == 0) return;

        LocalDateTime now = LocalDateTime.now();

        for (File file : files) {
            CodeSnippet snippet;

            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                snippet = (CodeSnippet) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                continue;
            }

            if (snippet.getExpiry().isBefore(now)) {
                try {
                    Files.delete(file.toPath());
                } catch (IOException ignored) {

                }
            }
        }
    }
}
