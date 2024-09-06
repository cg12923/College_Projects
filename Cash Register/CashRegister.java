New! Keyboard shortcuts … Drive keyboard shortcuts have been updated to give you first-letters navigation
//********************************************************************
//
//  Developer:     Christian Garcia
//
//  Project #:     Three
//
//  File Name:     CashRegister.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      09/22/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  Java 1.8
//
//  Description:   This java class provides methods that simulate cash register
//                 features. Such as showing a complete receipt including prices
//                 and taxes. As well as generating a receipt for the customer.
//
//********************************************************************



import java.io.*;
import java.util.*;


public class CashRegister {
    private List<StoreItem> itemList = new ArrayList<>();
    private double taxRate = 0.0825; // 8.25%
    private String cashierFileName;
    private Scanner scanner;
	//private List<StoreItem> selectedItems; 

    //***************************************************************
    //
    //  Method:       CashRegister
    //
    //  Description:  Constructor for the CashRegister class. Initializes the cashier file name.
    //
    //  Parameters:	cashierFileName
    //
    //
    //  Returns:      N/A
    //
    //**************************************************************
    
    public CashRegister(String cashierFileName) {
        this.cashierFileName = cashierFileName;
    }

    //***************************************************************
    //
    //  Method:     purchaseItem  
    // 
    //  Description:  Adds the item chosen by the user to the item list
    //
    //  Parameters:   item
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void purchaseItem(StoreItem item) {
        itemList.add(item);
    }

    //***************************************************************
    //
    //  Method:      getTotal 
    // 
    //  Description:  Gets the total of all the items chosen 
    //
    //  Parameters:   None
    //
    //  Returns:     The total of the itemList
    //
    //**************************************************************
    
    public double getTotal() {
        return itemList.stream().mapToDouble(StoreItem::getPrice).sum();
    }

    //***************************************************************
    //
    //  Method:       showItems
    // 
    //  Description:  Shows the items in the itemList
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    
    public void showItems() {
    	System.out.println("Items in cart:");
        itemList.forEach(item -> System.out.println(item.getItemDescription()));
    }

    //***************************************************************
    //
    //  Method:       clearRegister
    // 
    //  Description:  It clears the itemList
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    
    public void clearRegister() {
        itemList.clear();
    }
    
  


    //***************************************************************
    //
    //  Method:      checkOut 
    // 
    //  Description:  This method checks out the user on the items they chosen
    //                and provides the total price, taxes and the final price. It
    //                then lets the user either confirm or deny the purchase
    //
    //  Parameters:   Scanner scanner
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    
    public void checkOut(Scanner scanner) {
        double total = getTotal();
        double tax = total * taxRate;
        double finalPrice = total + tax;
       

        System.out.println("\nItems Purchased:");
        itemList.stream()
                .sorted(Comparator.comparing(StoreItem::getItemDescription))
                .forEach(item -> System.out.println(item.getItemDescription()));

        String formattedTotal = String.format(Locale.US, "%.2f", total);
        String formattedFinalPrice = String.format(Locale.US, "%.2f", finalPrice);
        System.out.println("Total Price: $" + formattedTotal);
        System.out.println("Tax (8.25%): $" + tax);
        System.out.println("Final Price: $" + formattedFinalPrice);

        while (true) {
            System.out.print("Confirm your purchase (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                // Generate receipt and save it to Project3-Output.txt
                generateReceipt(total, tax, finalPrice, scanner);

                // Clear the cash register
                clearRegister();

                System.out.println("Purchase completed. Receipt generated.");
                break; // Exit the loop on valid input
            } else if (confirmation.equalsIgnoreCase("no")) {
                cancelPurchase(itemList);
                break; // Exit the loop on valid input
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }

        }
    }


    //***************************************************************
    //
    //  Method:       generateReceipt
    // 
    //  Description:  This method generates a receipt including all the
    //                information presented to the user at the check out
    //                along with the date and the name of the cashier.
    //
    //  Parameters:   double total, double tax, double finalPrice, Scanner scanner
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    
    public void generateReceipt(double total, double tax, double finalPrice, Scanner scanner) {
    	try (PrintWriter writer = new PrintWriter("C:\\Java\\Project3-Output.txt")) {
    		
            writer.println("Receipt Date: " + new Date());
            writer.println("Cashier: " + getRandomCashierName(scanner));
            writer.println("Items Purchased:");
            itemList.stream()
                    .sorted(Comparator.comparing(StoreItem::getItemDescription))
                    .forEach(item -> writer.println(item.getItemDescription()));
            
            String formattedTotal = String.format(Locale.US, "%.2f", total);
            String formattedFinalPrice = String.format(Locale.US, "%.2f", finalPrice);
            writer.println("Total Price: $" + formattedTotal);
            writer.println("Tax (8.25%): $" + tax);
            writer.println("Final Price: $" + formattedFinalPrice);
    	} catch (FileNotFoundException e) {
            System.err.println("Error: Receipt file not found or cannot be created.");
            e.printStackTrace();
        }
    }



    //***************************************************************
    //
    //  Method:  getRandomCashierName     
    // 
    //  Description:  Generates a random cashier name from the list 
    //                of cashiers
    //
    //  Parameters:   Scanner scanner
    //
    //  Returns:      the random cashier name 
    //
    //**************************************************************
    
    public String getRandomCashierName(Scanner scanner) {
        List<String> cashiers = readCashierFile();
        Random random = new Random();
        int index = random.nextInt(cashiers.size());
        return cashiers.get(index);
    }

    //***************************************************************
    //
    //  Method:    readCashierFile   
    // 
    //  Description:  This method reads the cashier file
    //
    //  Parameters:   None
    //
    //  Returns:      cashiers 
    //
    //**************************************************************
    
    public List<String> readCashierFile() {
        List<String> cashiers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(cashierFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into tokens based on spaces
                String[] tokens = line.split(" ");
                
                // Add the tokens to the list
                cashiers.addAll(Arrays.asList(tokens));
            }
        } catch (IOException e) {
            System.err.println("Error reading the cashier file: " + e.getMessage());
            e.printStackTrace();
        }
        return cashiers;
    }
    
    
    //***************************************************************
    //
    //  Method:    cancelPurchase  
    // 
    //  Description:  This method updates the inventory by adding the items
    //                back after a purchase is has been canceled. 
    //
    //  Parameters:   List<StoreItem> storeItems
    //
    //  Returns:      None
    //
    //**************************************************************
    public void cancelPurchase(List<StoreItem> storeItems) {
        for (StoreItem purchasedItem : itemList) {
            // Find the corresponding item in the storeItems list
            StoreItem storeItem = storeItems.stream()
                    .filter(item -> item.getItemNumber() == purchasedItem.getItemNumber())
                    .findFirst()
                    .orElse(null);

            if (storeItem != null) {
                // Increase the inventory count for each item in the cash register by 1
                storeItem.increaseInventory(1);
            }
        }

        // Clear the items in the cash register
        itemList.clear();
        System.out.println("Canceled purchase. Items returned to inventory.");
    }

    
    public List<StoreItem> getItems() {
        return itemList;
    }

}
