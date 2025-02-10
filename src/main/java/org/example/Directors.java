package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class Directors {
    private Map<String, String> directorMap; // Stores directorId -> directorName

    // Constructor: Reads CSV and maps directorId to directorName using Streams
    public Directors() {
        String csvFile = "D:\\data\\directors_large.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            directorMap = br.lines()
                    .map(line -> line.split(",")) // Split each line into tokens
                    .filter(tokens -> tokens.length >= 2) // Ensure valid data
                    .collect(Collectors.toMap(tokens -> tokens[0].trim(), tokens -> tokens[1].trim())); // Store in Map
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Function to get director name by ID using Streams
    public String getDirectorById(String directorId) {
        return directorMap.getOrDefault(directorId.trim(), "Unknown Director");
    }
    public String getDirectorIdByName(String directorName) {
        return directorMap.entrySet().stream()
                .filter(entry -> entry.getValue().equalsIgnoreCase(directorName)) // ✅ Case-insensitive match
                .map(Map.Entry::getKey) // ✅ Get directorId
                .findFirst()
                .orElse(null); // ✅ Return null if not found
    }

}
