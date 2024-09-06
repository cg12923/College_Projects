New! Keyboard shortcuts … Drive keyboard shortcuts have been updated to give you first-letters navigation
//********************************************************************
//
//  Developer:     Christian Garcia
//
//  Project #:     Eight
//
//  File Name:     Project8.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      11/06/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  Java 1.8
//
//  Description:   This java program reads in data from a file of gas prices through out
//                 the years and performs calculations.
//********************************************************************

import java.io.File;
import java.util.List;

public class Program8 {
	
	//***************************************************************
    //
    //  Method:        Main method
    // 
    //  Description:  This method runs the java program
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public static void main(String[] args) {
    	Program8 obj = new Program8();
    	obj.developerInfo();
    	obj.runGasPriceAnalysis();
    }
    
    
    //***************************************************************
    //
    //  Method:        runGasPriceAnalysis()
    // 
    //  Description:  This method calls the classes to perform the reading of the file
    //                and then the calculations need to be made.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void runGasPriceAnalysis() {
        try {
            File inputFile = new File("Program8.txt");
            GasPriceDataReader dataReader = new GasPriceDataReader();
            List<GasPriceRecord> gasPriceRecords = dataReader.readGasPriceData(inputFile);

            GasPriceAnalyzer analyzer = new GasPriceAnalyzer(gasPriceRecords);

            analyzer.calculateAveragePricePerYear();
            analyzer.calculateAveragePricePerMonth();
            analyzer.findHighestAndLowestPricesPerYear();
            analyzer.generateSortedPriceLists();
        
        } catch (Exception e) {
         
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }



    
  //***************************************************************
    //
    //  Method:       developerInfo 
    // 
    //  Description:  The developer information method of the program
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************

    public void developerInfo() {
        System.out.println("Name:    Christian Garcia");
        System.out.println("Course:  COSC 4301 Modern Programming");
        System.out.println("Project: Eight\n\n");
    }
}