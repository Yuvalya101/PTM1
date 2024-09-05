package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IOSearcher {

    public static boolean search(String word,String... fileNames){

        for (String file : fileNames) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                // Read the file line by line
                while ((line = br.readLine()) != null) {
                    // Check if the line contains the word
                    if (line.contains(word)) {
                        return true; // Word found
                    }
                }
            }
            catch (IOException e) {
                // Handle exception, could be file not found, access issue, etc.
                System.err.println("Error reading file " + file + ": " + e.getMessage());
            }
        }

        return false;

    }

}
