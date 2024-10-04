package com.spamclassifier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public List<Email> loadDataset(String filePath) throws IOException {
        List<Email> emails = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length < 2) continue; // Skip invalid lines
            String content = parts[1].trim();
            boolean isSpam = parts[0].trim().equalsIgnoreCase("spam");
            emails.add(new Email(content, isSpam));
        }
        reader.close();
        return emails;
    }
}
