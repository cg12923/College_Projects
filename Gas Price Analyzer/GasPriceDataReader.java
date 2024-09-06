New! Keyboard shortcuts … Drive keyboard shortcuts have been updated to give you first-letters navigation
//********************************************************************
//
//  Developer:     Christian Garcia
//
//  Project #:     Eight
//
//  File Name:     GasPriceDataReader.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      11/06/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  Java 1.8
//
//  Description:   This class reads in the data file 
//********************************************************************

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GasPriceDataReader {

    //***************************************************************
    //  Method:        readGasPriceData
    // 
    //  Description:   Reads gas price data from the specified file, parses the data, and returns a list of GasPriceRecord objects.
    //
    //  Parameters:    file 
    //
    //  Returns:       A List of GasPriceRecord 
    //
    //  
    //**************************************************************
    public List<GasPriceRecord> readGasPriceData(File file) {
        List<GasPriceRecord> gasPriceRecords = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String dateStr = parts[0];
                    double price;

                    try {
                        price = Double.parseDouble(parts[1]);
                    } catch (NumberFormatException e) {
                        // Handle the exception or log it as needed
                        e.printStackTrace();
                        price = 0; // Assign a default value, e.g., 0
                    }

                    GasPriceRecord record = new GasPriceRecord(dateStr, price);
					gasPriceRecords.add(record);
                }
            }
        } catch (IOException e) {
            // Handle or log the IOException as needed
            e.printStackTrace();
        }

        return gasPriceRecords;
    }
}