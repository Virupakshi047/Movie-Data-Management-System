package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Arrays;

public class Actors {
    private Map<String, String> actorMap; // Stores actorId -> "actorName,birthYear"

    public Actors() {
        String csvFile = "D:\\data\\actors_large.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            actorMap = br.lines()
                    .map(line -> line.split(",")) // Split each line into an array
                    .filter(tokens -> tokens.length >= 2) // Ensure valid data
                    .collect(Collectors.toMap(tokens -> tokens[0].trim(), tokens -> tokens[1].trim())); // Store in


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

    public void getMostFrequentActors(List<String[]> movieData) {
        // Count occurrences of each actor in all movies
        Map<String, Long> actorCount = movieData.stream()
                .map(row -> row[7].replaceAll("\"", "").split(","))
                .flatMap(Arrays::stream)
                .map(String::trim)
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));
        long maxMovies = actorCount.values().stream().max(Long::compare).orElse(0L);

        List<String> mostFrequentActors = actorCount.entrySet().stream()
                .filter(entry -> entry.getValue() == maxMovies)
                .map(entry -> actorMap.getOrDefault(entry.getKey(), "Unknown Actor") + " (" + entry.getValue() + " movies)")
                .collect(Collectors.toList());

        System.out.println("\nActor Who Worked in Most Movies:");
        mostFrequentActors.forEach(System.out::println);
    }


}
