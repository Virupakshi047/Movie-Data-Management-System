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
    public List<String> getActorsByIds(String actorIds) {
        return Arrays.stream(actorIds.split(","))
                .map(id -> id.trim())
                .map(id -> actorMap.getOrDefault(id, "Unknown Actor"))
                .collect(Collectors.toList());
    }
}
