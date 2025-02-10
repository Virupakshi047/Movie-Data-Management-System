package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Arrays;

public class Actors {
    private Map<String, String> actorMap; // Stores actorId -> actorName

    // Constructor: Reads CSV and creates a Map using Streams
    public Actors() {
        String csvFile = "D:\\data\\actors_large.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            actorMap = br.lines()
                    .map(line -> line.split(",")) // Split each line into an array
                    .filter(tokens -> tokens.length >= 2) // Ensure valid data
                    .collect(Collectors.toMap(tokens -> tokens[0].trim(), tokens -> tokens[1].trim())); // Store in Map
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to get a list of actor names by actor IDs (comma-separated)
    public List<String> getActorsByIds(String actorIds) {
        return Arrays.stream(actorIds.split(",")) // Split actorIds using ","
                .map(String::trim) // Trim spaces
                .map(actorMap::get) // Fetch actor name from Map
                .filter(name -> name != null) // Ignore null values
                .collect(Collectors.toList()); // Collect as List
    }
}
