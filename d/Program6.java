import java.util.Scanner;
import java.util.InputMismatchException;

public class Program6 {

    public static void main(String[] args) {
    	Program6 calculator = new Program6();
    	calculator.developerInfo();
        calculator.runFractionCalculator();
    }

    public void runFractionCalculator() {
    	try (Scanner scanner = new Scanner(System.in)) {
    	    System.out.println("Welcome to the Fraction Calculator!");
    	    boolean continueCalculations = true; // Initialize the flag to true
    	    
    	    while (continueCalculations) { // Use the flag as the loop condition
    	        int numerator1 = getNumerator(scanner);
    	        int denominator1 = getDenominator(scanner);

    	        // Check if both numerator and denominator are zero, and exit if true
    	        if (numerator1 == 0 && denominator1 == 0) {
    	            continueCalculations = false; // Set the flag to false to exit the loop
    	            break;
    	        }

    	        int numerator2 = getNumerator(scanner);
    	        int denominator2 = getDenominator(scanner);

    	        try {
    	            Fraction fraction1 = new Fraction(numerator1, denominator1);
    	            Fraction fraction2 = new Fraction(numerator2, denominator2);

    	            int choice = getUserChoice(scanner);

    	            Fraction result = performOperation(fraction1, fraction2, choice);

    	            displayResult(result);
    	        } catch (IllegalArgumentException ex) {
    	            System.out.println("Invalid input. " + ex.getMessage());
    	        }
    	    }
    	}

    	System.out.println("Exiting the Fraction Calculator. Thank you!");

    }
    


    private int getNumerator(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter the numerator of the fraction (0 for numerator and denominator to exit): ");
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine(); // Clear the invalid input from the scanner
            }
        }
    }

    private int getDenominator(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter the denominator of the fraction: ");
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine(); // Clear the invalid input from the scanner
            }
        }
    }

    private int getUserChoice(Scanner scanner) {
        boolean choiceIsInvalid = true; // Initialize the flag to true

        while (choiceIsInvalid) { // Use the flag as the loop condition
            try {
                System.out.println("Select an operation:");
                System.out.println("1. Add Fractions");
                System.out.println("2. Subtract Fractions");
                System.out.println("3. Multiply Fractions");
                System.out.println("4. Divide Fractions");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                if (choice >= 1 && choice <= 4) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please select a valid operation (1 to 4).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer choice.");
                scanner.nextLine(); // Clear the invalid input from the scanner
            }
        }

        return -1; // Return a default value if needed
    }



    private Fraction performOperation(Fraction fraction1, Fraction fraction2, int choice) {
        switch (choice) {
            case 1:
                return fraction1.addFraction(fraction2);
            case 2:
                return fraction1.subtractFraction(fraction2);
            case 3:
                return fraction1.multiplyFraction(fraction2);
            case 4:
                return fraction1.divideFraction(fraction2);
            default:
                System.out.println("Invalid choice. Please select a valid operation.");
                return null;
        }
    }

    private void displayResult(Fraction result) {
        if (result != null) {
            System.out.println("Result: " + result.toString());
            result.displayImproper();
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
        System.out.println("Project: Six\n\n");
    }
}
