package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Singleton Instance of Movie
        Movie mv = Movie.getInstance();
        Actors ac = new Actors();
        Directors dc = new Directors();

        while (true) {
            System.out.println("\n===== Movie Management System =====");
            System.out.println("1. Get Movie Information");
            System.out.println("2. Get Top 10 Rated Movies");
            System.out.println("3. Get Movies by Genre");
            System.out.println("4. Get Movies by Director");
            System.out.println("5. Get Movies by Release Year");
            System.out.println("6. Get Movies by Release Year Range");
            System.out.println("7. Add a New Movie");
            System.out.println("8. Update Movie Rating");
            System.out.println("9. Delete a Movie");
            System.out.println("10. Sort and Display 15 Movies by Release Year");
            System.out.println("11. Get Top 5 Directors with Most Movies");
            System.out.println("12. Get Actors Who Worked in Multiple Movies");
            System.out.println("14. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:  // Get Movie Information
                    System.out.print("Enter Movie ID or Title: ");
                    String searchKey = scanner.nextLine();
                    mv.getMovieInfo(searchKey, ac, dc);
                    break;

                case 2:  // Get Top 10 Rated Movies
                    mv.getTop10RatedMovies();
                    break;

                case 3:  // Get Movies by Genre
                    System.out.print("Enter Genre: ");
                    String genre = scanner.nextLine();
                    mv.getMoviesByGenre(genre);
                    break;

                case 4:  // Get Movies by Director
                    System.out.print("Enter Director Name: ");
                    String directorName = scanner.nextLine();
                    mv.getMoviesByDirector(directorName, dc);
                    break;

                case 5:  // Get Movies by Release Year
                    System.out.print("Enter Release Year: ");
                    String year = scanner.next();
                    mv.getMoviesByReleaseYear(year);
                    break;

                case 6:  // Get Movies by Release Year Range
                    System.out.print("Enter Release Year Range (YYYY-YYYY): ");
                    String range = scanner.nextLine();
                    mv.getMoviesByYearRange(range);
                    break;

                case 7:  // Add a New Movie
                    System.out.println("Enter Movie Details:");

                    System.out.print("Movie ID: ");
                    String movieId = scanner.nextLine();

                    System.out.print("Title: ");
                    String title = scanner.nextLine();

                    System.out.print("Release Year: ");
                    String releaseYear = scanner.nextLine();

                    System.out.print("Genre: ");
                    String genere = scanner.nextLine();

                    System.out.print("Rating: ");
                    String rating = scanner.nextLine();

                    System.out.print("Duration (in minutes): ");
                    String duration = scanner.nextLine();

                    System.out.print("Director ID: ");
                    String directorId = scanner.nextLine();

                    System.out.print("Actor IDs (comma-separated): ");
                    String actorIds = scanner.nextLine();

                    // Call addMovie() method with separate parameters
                    mv.addMovie(movieId, title, releaseYear, genere, rating, duration, directorId, actorIds);
                    break;

                case 8:  // Update Movie Rating
                    System.out.print("Enter Movie ID: ");
                    String movieIde = scanner.nextLine();
                    System.out.print("Enter New Rating: ");
                    String newRating = scanner.next();
                    mv.updateMovieRating(movieIde, newRating);
                    break;

                case 9:  // Delete a Movie
                    System.out.print("Enter Movie ID to delete: ");
                    String delMovieId = scanner.nextLine();
                    mv.deleteMovie(delMovieId);
                    break;

                case 10: // Sort and Display 15 Movies by Release Year
                    mv.getTop15MoviesByYear();
                    break;

                case 11: // Get Directors with Most Movies
                    dc.getTop5DirectorsWithMostMovies(mv);
                    break;

                case 12: // Get Actors Who Worked in Multiple Movies
                    ac.getMostFrequentActors(mv.getData());
                    break;
                case 14: // Exit Program
                    System.out.println("Exiting... Thank you!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
