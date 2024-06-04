//********************************************************************
//
//  Author:        Christian Garcia
//
//  Project #:     Four
//
//  File Name:     SortingThread.java
//
//  Course:        COSC 4302 Operating Systems
//
//  Due Date:      10/05/2023
//
//  Instructor:    Prof. Fred Kumi
//
//  Java Version:  Java Version 1.8.0_361
//
//  Description:   This is a class definition for a thread that performs 
//                 sorting on a specified portion of an integer array 
//
//********************************************************************

import java.util.Arrays;

public class SortingThread extends Thread {
    private int[] array;
    private int start;
    private int end;
    private int[] sortedArray;

    
  //***************************************************************
    //
    //  Method:      SortingThread
    // 
    //  Description:  Constructor
    //
    //  Parameters:   int[] array, int start, int end
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public SortingThread(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    
  //***************************************************************
    //
    //  Method:       run
    // 
    //  Description: This method sorts the array
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void run() {
        try {
            // Perform sorting on the specified portion of the array
            Arrays.sort(array, start, end);
            sortedArray = Arrays.copyOfRange(array, start, end);
        } catch (IndexOutOfBoundsException e) {
            // Handle the exception, e.g., log an error message or take appropriate action.
            System.err.println("Error: IndexOutOfBoundsException in SortingThread: " + e.getMessage());
        }
    }

    
  //***************************************************************
    //
    //  Method:       getSortedArray
    // 
    //  Description:  This method returns the sorted array when called.
    //
    //  Parameters:  None
    //
    //  Returns:      sortedArray
    //
    //**************************************************************
    public int[] getSortedArray() {
        return sortedArray;
    }
}
