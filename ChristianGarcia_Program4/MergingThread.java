//********************************************************************
//
//  Author:        Christian Garcia
//
//  Project #:     Four
//
//  File Name:     MergingThread.java
//
//  Course:        COSC 4302 Operating Systems
//
//  Due Date:      10/05/2023
//
//  Instructor:    Prof. Fred Kumi
//
//  Java Version:  Java Version 1.8.0_361
//
//  Description:   This is a class definition for a thread that merges
//                 two sorted integer arrays into one sorted array.
//
//********************************************************************


public class MergingThread extends Thread {
    private int[] left;
    private int[] right;
    private int[] result;

    
  //***************************************************************
    //
    //  Method:       MergingThread
    // 
    //  Description:  Constructor
    //
    //  Parameters:   int[] left, int[] right, int[] result
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public MergingThread(int[] left, int[] right, int[] result) {
        this.left = left;
        this.right = right;
        this.result = result;
    }

    
  //***************************************************************
    //
    //  Method:       run
    // 
    //  Description:  This method merges the two sorted arrays
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void run() {
        try {
            // Merge the two sorted arrays into one sorted array
            int i = 0, j = 0, k = 0;
            while (i < left.length && j < right.length) {
                if (left[i] < right[j]) {
                    result[k++] = left[i++];
                } else {
                    result[k++] = right[j++];
                }
            }

            while (i < left.length) {
                result[k++] = left[i++];
            }

            while (j < right.length) {
                result[k++] = right[j++];
            }
        } catch (IndexOutOfBoundsException e) {
            // Handle the exception, e.g., log an error message or take appropriate action.
            System.err.println("Error: IndexOutOfBoundsException in MergingThread: " + e.getMessage());
        }
    }
}
