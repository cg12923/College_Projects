New! Keyboard shortcuts … Drive keyboard shortcuts have been updated to give you first-letters navigation
//********************************************************************
//
//  Developer:     Christian Garcia
//
//  Project #:     Eight
//
//  File Name:     GasPriceAnalyzer.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      11/06/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  Java 1.8
//
//  Description:   This class performs the calculations of average price per year and month
//                 and sorts the data from highest to lowest and vice versa
//********************************************************************

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.PrintWriter;



public class GasPriceAnalyzer {
    private List<GasPriceRecord> gasPriceRecords;

    //***************************************************************
    //
    //  Method:        GasPriceAnalyzer
    // 
    //  Description:  Constructor
    //
    //  Parameters:   List<GasPriceRecord> gasPriceRecords
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public GasPriceAnalyzer(List<GasPriceRecord> gasPriceRecords) {
        this.gasPriceRecords = gasPriceRecords;
    }

    
    //***************************************************************
    //
    //  Method:        calculateAveragePricePerYear
    // 
    //  Description:  This method calculates the average price per year
    //                and prints to the console
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void calculateAveragePricePerYear() {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        Map<String, List<GasPriceRecord>> recordsByYear = gasPriceRecords.stream()
                .collect(Collectors.groupingBy(record -> yearFormat.format(record.getDate())));
        System.out.println("Average Price Per Year:");
        for (Map.Entry<String, List<GasPriceRecord>> entry : recordsByYear.entrySet()) {
            String year = entry.getKey();
            List<GasPriceRecord> records = entry.getValue();
            double averagePrice = records.stream().mapToDouble(GasPriceRecord::getPrice).average().orElse(0);

            // Format the average price to two decimal places
            String formattedAveragePrice = String.format("%.2f", averagePrice);

            System.out.println("Year: " + year + ", Average Price: " + formattedAveragePrice);
        }
    }


    
    //***************************************************************
    //
    //  Method:        calculateAveragePricePerMonth
    // 
    //  Description:  This method calculates the average price per month
    //                and prints to the console
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void calculateAveragePricePerMonth() {
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MM-yyyy");

        Map<String, List<GasPriceRecord>> recordsByMonthYear = gasPriceRecords.stream()
                .collect(Collectors.groupingBy(record -> monthYearFormat.format(record.getDate())));
        System.out.println("\nAverage Price Per Month:");
        for (Map.Entry<String, List<GasPriceRecord>> entry : recordsByMonthYear.entrySet()) {
            String monthYear = entry.getKey();
            List<GasPriceRecord> records = entry.getValue();
            double averagePrice = records.stream().mapToDouble(GasPriceRecord::getPrice).average().orElse(0);
         // Format the average price to two decimal places
            String formattedAveragePrice = String.format("%.2f", averagePrice);
            System.out.println("Month-Year: " + monthYear + ", Average Price: " +  formattedAveragePrice);
        }
    }

    
    //***************************************************************
    //
    //  Method:     findHighestAndLowestPricesPerYear   
    // 
    //  Description:  This method finds the highest and lowest price per year
    //                and prints to the console
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void findHighestAndLowestPricesPerYear() {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        Map<String, List<Map.Entry<Date, Double>>> highestLowestPricesByYear = new HashMap<>();

        Map<String, List<GasPriceRecord>> recordsByYear = gasPriceRecords.stream()
                .collect(Collectors.groupingBy(record -> yearFormat.format(record.getDate())));
        System.out.println("\nHighest and Lowest Prices per Year:");
        for (Map.Entry<String, List<GasPriceRecord>> entry : recordsByYear.entrySet()) {
            String year = entry.getKey();
            List<GasPriceRecord> records = entry.getValue();

            Optional<GasPriceRecord> maxPriceRecord = records.stream().max(Comparator.comparing(GasPriceRecord::getPrice));
            Optional<GasPriceRecord> minPriceRecord = records.stream().min(Comparator.comparing(GasPriceRecord::getPrice));

            if (maxPriceRecord.isPresent() && minPriceRecord.isPresent()) {
                Date maxDate = maxPriceRecord.get().getDate();
                double maxPrice = maxPriceRecord.get().getPrice();
                Date minDate = minPriceRecord.get().getDate();
                double minPrice = minPriceRecord.get().getPrice();

                // Format maxPrice and minPrice to two decimal places
                String formattedMaxPrice = String.format("%.2f", maxPrice);
                String formattedMinPrice = String.format("%.2f", minPrice);

                List<Map.Entry<Date, Double>> pricesList = highestLowestPricesByYear.computeIfAbsent(year, k -> new ArrayList<>());
                pricesList.add(new AbstractMap.SimpleEntry<>(maxDate, Double.parseDouble(formattedMaxPrice)));
                pricesList.add(new AbstractMap.SimpleEntry<>(minDate, Double.parseDouble(formattedMinPrice)));
            }

        }

        for (Map.Entry<String, List<Map.Entry<Date, Double>>> entry : highestLowestPricesByYear.entrySet()) {
            String year = entry.getKey();
            List<Map.Entry<Date, Double> > priceEntries = entry.getValue();
            
            for (Map.Entry<Date, Double> priceEntry : priceEntries) {
                Date date = priceEntry.getKey();
                double price = priceEntry.getValue();
                String formattedPrice = String.format("%.2f", price);
                if (price == Collections.max(priceEntries, Comparator.comparingDouble(Map.Entry::getValue)).getValue()) {
                    System.out.println("Year: " + year + ", Highest Price: " + formattedPrice + " on " + date);
                }
                if (price == Collections.min(priceEntries, Comparator.comparingDouble(Map.Entry::getValue)).getValue()) {
                    System.out.println("Year: " + year + ", Lowest Price: " + formattedPrice + " on " + date);
                }
            }
        }
    }



    
    //***************************************************************
    //
    //  Method:        generateSortedPriceLists
    // 
    //  Description:   This list sorts the data from highest to lowest and 
    //                 lowest to highest
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void generateSortedPriceLists() {
        List<GasPriceRecord> sortedRecordsAsc = gasPriceRecords.stream()
                .sorted(Comparator.comparing(GasPriceRecord::getPrice))
                .collect(Collectors.toList());

        List<GasPriceRecord> sortedRecordsDesc = gasPriceRecords.stream()
                .sorted(Comparator.comparing(GasPriceRecord::getPrice).reversed())
                .collect(Collectors.toList());

        saveToTextFile("SortedPricesLowestToHighest.txt", sortedRecordsAsc);
        saveToTextFile("SortedPricesHighestToLowest.txt", sortedRecordsDesc);
    }

    
    //***************************************************************
    //
    //  Method:        saveToTextFile
    // 
    //  Description:  This method saves the sorted list into text files
    //
    //  Parameters:   String fileName, List<GasPriceRecord> records
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    private void saveToTextFile(String fileName, List<GasPriceRecord> records) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (GasPriceRecord record : records) {
            	writer.println("Date: " + record.getDate() + " Price: " + String.format("%.2f", record.getPrice()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}