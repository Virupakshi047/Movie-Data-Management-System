package org.example;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Movie {
    private static Movie instance;
    public List<String[]> data;
    private Movie() {
        data = new ArrayList<>();
        loadMoviesFromCSV();
    }

    public static Movie getInstance() {
        if (instance == null) {
            instance = new Movie();
        }
        return instance;
    }


    private void loadMoviesFromCSV() {
        String csvFile = "D:\\data\\movies_large.csv";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                data.add(tokens);
            }
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
                .skip(1)
                .filter(row -> row.length >= 5)
                .filter(row -> row[4].matches("\\d+(\\.\\d+)?"))
                .sorted((a, b) -> Double.compare(Double.parseDouble(b[4].trim()), Double.parseDouble(a[4].trim())))
                .limit(10)
                .forEach(row -> System.out.println(row[1].trim() + " (Rating: " + row[4].trim() + ")"));

        System.out.println("---------------------------\n");
    }

    public void getMoviesByGenre(String genre) {
        System.out.println("\nMovies in Genre: " + genre);
        System.out.println("---------------------------");

        data.stream()
                .skip(1)
                .filter(row -> row.length >= 4)
                .filter(row -> row[3].trim().equalsIgnoreCase(genre))
                .forEach(row -> System.out.println(row[1].trim() + " (" + row[2].trim() + ")"));

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
                .skip(1)
                .filter(row -> row.length >= 7)
                .filter(row -> row[6].trim().equals(directorId))
                .forEach(row -> System.out.println(row[1].trim() + " (" + row[2].trim() + ")"));

        System.out.println("---------------------------\n");
    }
    public void getMoviesByReleaseYear(String year){
        System.out.println("\n Movies released in the year "+year);
        System.out.println("--------------------------------");

        if(year==null){
            System.out.println("Movies not found");
            return;
        }
        data.stream()
                .skip(1)
                .filter(row->row.length>=7)
                .filter(row->row[2].trim().equals(year))
                .forEach(row->System.out.println(row[1].trim()));
        System.out.println("--------------------------------\n");
    }

    public void getMoviesByYearRange(String yearRange) {
        // Split input string and parse start & end years
        String[] years = yearRange.split("-");

        if (years.length != 2) {
            System.out.println("Invalid input format! Please use 'YYYY-YYYY' format.");
            return;
        }
        if(Integer.parseInt(years[1])>2025){
            System.out.println("Enter year within 2025");
            return;
        }

        try {
            int startYear = Integer.parseInt(years[0].trim());
            int endYear = Integer.parseInt(years[1].trim());

            System.out.println("\nMovies Released Between " + startYear + " and " + endYear);
            System.out.println("---------------------------");

            data.stream()
                    .skip(1)
                    .filter(row -> row.length >= 3)
                    .filter(row -> row[2].matches("\\d{4}"))
                    .map(row -> new AbstractMap.SimpleEntry<>(row, Integer.parseInt(row[2].trim())))
                    .filter(entry -> entry.getValue() >= startYear && entry.getValue() <= endYear)
                    .map(Map.Entry::getKey) // ✅ Get original row back
                    .forEach(row -> System.out.println(row[1].trim() + " (" + row[2].trim() + ")"));

            System.out.println("---------------------------\n");

        } catch (NumberFormatException e) {
            System.out.println("Invalid year format! Please enter years in numeric format (e.g., '2014-2020').");
        }
    }
    public void addMovie(String movieId, String title, String releaseYear, String genre, String rating, String duration, String directorId, String actorIds) {
        // Create a new movie entry as an array
        String[] newMovie = {movieId, title, releaseYear, genre, rating, duration, directorId, actorIds};

        // Add the new movie to the list
        data.add(newMovie);

        System.out.println("\nMovie Added Successfully!");
        System.out.println("---------------------------");
        System.out.println("Title: " + title);
        System.out.println("Year: " + releaseYear);
        System.out.println("Genre: " + genre);
        System.out.println("Rating: " + rating);
        System.out.println("Duration: " + duration + " min");
        System.out.println("Director ID: " + directorId);
        System.out.println("Actor IDs: " + actorIds);
        System.out.println("---------------------------\n");
    }

}
