package org.example;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Actors {

    // Public variable to store CSV data
    public List<String[]> data;

    // Constructor to read and store CSV data
    public Actors() {
        data = new ArrayList<>(); // Initialize the list
        String csvFile = "D:\\data\\actors_large.csv"; // File path
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                data.add(tokens); // Store each row in the list
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to print all data
    public void printData() {
        for (String[] row : data) {
            for (String value : row) {
                System.out.print(value.trim() + " | ");
            }
            System.out.println();
        }
    }


}