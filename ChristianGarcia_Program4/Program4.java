//********************************************************************
//
//  Author:        Christian Garcia
//
//  Project #:     Four
//
//  File Name:     Program2.java
//
//  Course:        COSC 4302 Operating Systems
//
//  Due Date:      10/05/2023
//
//  Instructor:    Prof. Fred Kumi
//
//  Java Version:  Java Version 1.8.0_361
//
//  Description:   This program sorts an array of numbers from least to greatest.
//                 The methods ask for user input and uses multi-threads to achieve 
//                 the sorting.
//
//********************************************************************

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Program4 {
	
	//***************************************************************
    //
    //  Method:       main
    // 
    //  Description:  The main method of the project.
    //
    //  Parameters:   String array
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public static void main(String[] args) {
        Program4 program = new Program4();
        Program4.developerInfo();
        program.runSortingProgram();
    }
    
  //***************************************************************
    //
    //  Method:       runSortingProgram()
    // 
    //  Description:  This method runs the sorting program by asking user
    //                how to create the array
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void runSortingProgram() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                int choice = getUserChoice(scanner);

                if (choice == 0) {
                    System.out.println("Program ending.");
                    break;
                } else if (choice == 1) {
                    int[] userArray = getUserArray(scanner);
                    int[] sortedArray = multithreadedSort(userArray);
                    displaySortedArray(sortedArray);
                } else if (choice == 2) {
                    int n = getUserInput(scanner);
                    int[] generatedArray = generateRandomArray(n);
                    int[] sortedArray = multithreadedSort(generatedArray);
                    displaySortedArray(sortedArray);
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle other exceptions appropriately
        }
    }


  //***************************************************************
    //
    //  Method:       getUserChoice
    // 
    //  Description:  This method shows the user the available options
    //                to create an array.
    //
    //  Parameters:   Scanner scanner
    //
    //  Returns:      scanner.nextInt() 
    //
    //**************************************************************
    public int getUserChoice(Scanner scanner) {
        System.out.println("Choose an option:");
        System.out.println("1. Enter your own array");
        System.out.println("2. Generate a random array");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }
    
    
  //***************************************************************
    //
    //  Method:       getUserArray
    // 
    //  Description:  It takes user input to create the array that needs to
    //                be created.
    //
    //  Parameters:   Scanner scanner
    //
    //  Returns:      userArray
    //
    //**************************************************************
    public int[] getUserArray(Scanner scanner) {
        System.out.print("Enter the integers separated by spaces: ");
        scanner.nextLine(); // Consume the newline character
        String input = scanner.nextLine();
        String[] strArray = input.split("\\s+");
        int[] userArray = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            try {
                userArray[i] = Integer.parseInt(strArray[i]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid input: " + strArray[i] + " is not a valid integer.");
                // Handle the error or ask the user to re-enter the input.
            }
        }
        return userArray;
    }
    
    
  //***************************************************************
    //
    //  Method:       getUserInput
    // 
    //  Description:  This method takes input from the user on how many integers
    //                need to randomly be generated to create the array to be sorted.
    //
    //  Parameters:   Scanner scanner
    //
    //  Returns:      n 
    //
    //**************************************************************
    public int getUserInput(Scanner scanner) {
        int n;
        while (true) {
            try {
                System.out.print("Enter the number of integers to be sorted (0 to exit): ");
                n = scanner.nextInt();
                break; // Break out of the loop if input is a valid integer
            } catch (java.util.InputMismatchException e) {
                // Handle the exception (invalid input)
                System.err.println("Error: Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        return n;
    }

    
    
  //***************************************************************
    //
    //  Method:       displaySortedArray
    // 
    //  Description:  This method prints out the sorted array.
    //
    //  Parameters:   int[] sortedArray
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void displaySortedArray(int[] sortedArray) {
        System.out.println("Sorted Array: " + Arrays.toString(sortedArray));
    }
    
    
  //***************************************************************
    //
    //  Method:       generateRandomArray
    // 
    //  Description:  This method generates the integers in for the random
    //                array.
    //
    //  Parameters:   int n
    //
    //  Returns:      arr 
    //
    //**************************************************************
    public static int[] generateRandomArray(int n) {
        int[] arr = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(99) + 1;
        }
        return arr;
    }

    
  //***************************************************************
    //
    //  Method:       multithreadedSort
    // 
    //  Description:  This method starts the thread that sorts the array
    //
    //  Parameters:   int[] unsortedArray
    //
    //  Returns:      sortedArray
    //
    //**************************************************************
    public static int[] multithreadedSort(int[] unsortedArray) {
        int[] sortedArray = new int[unsortedArray.length];

        // Create two sorting threads
        int mid = unsortedArray.length / 2;
        SortingThread thread1 = new SortingThread(unsortedArray, 0, mid);
        SortingThread thread2 = new SortingThread(unsortedArray, mid, unsortedArray.length);

        try {
            // Start sorting threads
            thread1.start();
            thread2.start();

            // Wait for sorting threads to finish
            thread1.join();
            thread2.join();

            // Merge sorted halves using the merging thread
            MergingThread mergingThread = new MergingThread(thread1.getSortedArray(), thread2.getSortedArray(), sortedArray);
            mergingThread.start();
            mergingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            System.err.println("Thread creation failed due to OutOfMemoryError: " + e.getMessage());
            // Handle the error appropriately, e.g., by exiting or informing the user.
        }

        return sortedArray;
    }
    
    
  //***************************************************************
    //
    //  Method:       developerInfo (Non Static)
    // 
    //  Description:  The developer information method of the program
	//                This method must be included in all projects.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //**************************************************************	
	public static void developerInfo()
    {
       System.out.println("Name:    Christian Garcia");
       System.out.println("Course:  COSC 4302 Operating Systems");
       System.out.println("Program: Four\n");
    } // End of the developerInfo method
}
