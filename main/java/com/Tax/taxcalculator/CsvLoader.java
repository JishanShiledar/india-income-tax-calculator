package com.Tax.taxcalculator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvLoader {
    public static List<Person> loadPeopleFromCsv() {
        try{
            Path csvPath = Paths.get("people.csv");
            if(!Files.exists(csvPath)){
                return List.of();
            }

            return Files.lines(csvPath)
                    .skip(1)
                    .map(line -> line.split(","))
                    .filter(parts -> parts.length >=2)
                    .map(parts -> new Person(
                            parts[0].trim(),
                            Double.parseDouble(parts[1].trim())
                    ))
                    .toList();
        } catch (Exception e){
            System.out.println("Error reading CSV: " + e.getMessage());
            return List.of();
        }
    }
}
