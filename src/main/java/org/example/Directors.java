package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Directors {
    private Map<String, String> directorMap; // Stores directorId -> directorName
    public List<String[]> data;

    // Constructor: Reads CSV and maps directorId to directorName using Streams
    public Directors() {
        data = new ArrayList<>();
        String csvFile = "D:\\data\\directors_large.csv";
        String lines;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            directorMap = br.lines()
                    .map(line -> line.split(",")) // Split each line into tokens
                    .filter(tokens -> tokens.length >= 2) // Ensure valid data
                    .collect(Collectors.toMap(tokens -> tokens[0].trim(), tokens -> tokens[1].trim())); // Store in Map
            while ((lines = br.readLine()) != null) {
                String[] tokens = lines.split(",");
                data.add(tokens);
            }
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
                .filter(entry -> entry.getValue().equalsIgnoreCase(directorName))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public void getTop5DirectorsWithMostMovies(Movie movieDB) {
        Map<String, Long> directorMovieCount = movieDB.getData().stream()
                .skip(1)
                .filter(row -> row.length > 6)
                .collect(Collectors.groupingBy(row -> row[6].trim(), Collectors.counting()));

        directorMovieCount.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .limit(5)
                .forEach(entry -> System.out.println(getDirectorById(entry.getKey()) + " → Movies: " + entry.getValue()));
    }

}
