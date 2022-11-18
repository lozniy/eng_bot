package com.eng_bot.springboot;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DictionaryReader {
    public List<String[]> read() throws IOException {
        CSVReader reader = new CSVReader(new FileReader("./data/docs/dic.csv"), ',', '"', 1);
        String[] nextLine;
        List<String[]> wordRows = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            wordRows.add(nextLine);
            System.out.println(nextLine[2]);
        }
        return wordRows;
    }
}
