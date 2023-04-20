package com.epam.esm.model.entity;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IOCertificate {

    private static List<String> readFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void saveToFile(String filename, Collection<String> collection) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            collection.forEach(writer::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
