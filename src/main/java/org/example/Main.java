package org.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.example.Actors;
import org.example.Directors;
import org.example.Movie;
public class Main {
    public static void main(String[] args) {
        Movie mv =  Movie.getInstance();

        Actors ac = new Actors();
        Directors dc = new Directors();

        mv.getMovieInfo("Movie 10",ac,dc);
//        mv.getTop10RatedMovies();
//        mv.getMoviesByGenre("C");
//        mv.getMoviesByDirector("Daniel Myers",dc);
//        mv.getMoviesByReleaseYear("1985");
//        mv.getMoviesByYearRange("2014-2020");
//        mv.addMovie("5002", "Inception 2", "2025", "Sci-Fi", "9.5", "160", "101", "461,1696,964");
//        mv.updateMovieRating("1221","1.3");
//        mv.getMovieInfo("Movie 1221",ac,dc);
//        mv.deleteMovie("7");
//        mv.getTop15MoviesByYear();

//        dc.getTop5DirectorsWithMostMovies(mv);
//        mv.showData();
    }

}