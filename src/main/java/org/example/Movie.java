package org.example;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Movie {
    private List<String[]> data; // List to store movie data

    // Constructor: Reads CSV and stores data
    public Movie() {
        String csvFile = "D:\\data\\movies_large.csv"; // File path

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            data = br.lines() // Read lines as a Stream
                    .map(line -> line.split(",")) // Split each line into tokens
                    .collect(Collectors.toList()); // Store in List
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to get movie details by ID or Title using Streams
    public void getMovieInfo(String searchKey, Actors actors, Directors directors) {
        Optional<String[]> movie = data.stream()
                .filter(row -> row.length >= 8)
                .filter(row -> row[0].trim().equalsIgnoreCase(searchKey) || row[1].trim().equalsIgnoreCase(searchKey))
                .findFirst();

        if (movie.isPresent()) {
            String[] row = movie.get();
            String movieId = row[0].trim();
            String title = row[1].trim();
            String releaseYear = row[2].trim();
            String genre = row[3].trim();
            String rating = row[4].trim();
            String duration = row[5].trim();
            String directorId = row[6].trim();
            String actorIds = row[7].trim();

            System.out.println("\n---------------------------");
            System.out.println("Movie Details:");
            System.out.println("ID: " + movieId);
            System.out.println("Title: " + title);
            System.out.println("Release Year: " + releaseYear);
            System.out.println("Genre: " + genre);
            System.out.println("Rating: " + rating);
            System.out.println("Duration: " + duration + " mins");

            // Fetch actors using the updated method (List of actors)
            List<String> movieActors = actors.getActorsByIds(actorIds);
            System.out.println("\nActors:");
            movieActors.forEach(actor -> System.out.println("  - " + actor));

            // Fetch director using streams
            String movieDirector = directors.getDirectorById(directorId);
            System.out.println("\nDirector:\n  - " + movieDirector);

            System.out.println("---------------------------\n");
        } else {
            System.out.println("Movie not found!");
        }
    }
    public void getTop10RatedMovies() {
        System.out.println("\nTop 10 Rated Movies:\n---------------------------");

        data.stream()
                .skip(1) // ✅ Skip the header row
                .filter(row -> row.length >= 5) // ✅ Ensure valid rows
                .filter(row -> row[4].matches("\\d+(\\.\\d+)?")) // ✅ Ensure rating is a valid number
                .sorted((a, b) -> Double.compare(Double.parseDouble(b[4].trim()), Double.parseDouble(a[4].trim()))) // ✅ Sort by rating (descending)
                .limit(10) // ✅ Get top 10
                .forEach(row -> System.out.println(row[1].trim() + " (Rating: " + row[4].trim() + ")"));

        System.out.println("---------------------------\n");
    }

    public void getMoviesByGenre(String genre) {
        System.out.println("\nMovies in Genre: " + genre);
        System.out.println("---------------------------");

        data.stream()
                .skip(1) // ✅ Skip the header row
                .filter(row -> row.length >= 4) // ✅ Ensure valid rows
                .filter(row -> row[3].trim().equalsIgnoreCase(genre)) // ✅ Match genre (case-insensitive)
                .forEach(row -> System.out.println(row[1].trim() + " (" + row[2].trim() + ")")); // ✅ Print title & year

        System.out.println("---------------------------\n");
    }
    public void getMoviesByDirector(String directorName, Directors directors) {
        System.out.println("\nMovies Directed by: " + directorName);
        System.out.println("---------------------------");

        // Find the director ID using their name
        String directorId = directors.getDirectorIdByName(directorName);

        if (directorId == null) {
            System.out.println("Director not found!");
            return;
        }

        data.stream()
                .skip(1) // ✅ Skip the header row
                .filter(row -> row.length >= 7) // ✅ Ensure valid rows
                .filter(row -> row[6].trim().equals(directorId)) // ✅ Match director ID
                .forEach(row -> System.out.println(row[1].trim() + " (" + row[2].trim() + ")")); // ✅ Print title & year

        System.out.println("---------------------------\n");
    }



}
