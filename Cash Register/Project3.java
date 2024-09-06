New! Keyboard shortcuts … Drive keyboard shortcuts have been updated to give you first-letters navigation
//********************************************************************
//
//  Developer:     Christian Garcia
//
//  Project #:     Three
//
//  File Name:     Project3.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      09/22/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  Java 1.8
//
//  Description:   This java program simulates a cash register and the user is
//                 is able to see the inventory available and select items they
//                 want to purchase. It also checks out the user for the items
//                 and prints a receipt to them.
//********************************************************************




import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Project3 {
    public static void main(String[] args) {
    	Project3 project = new Project3();
    	project.developerInfo();
        project.runProgram();
    }
    
    //***************************************************************
    //
    //  Method:       runProgram
    // 
    //  Description:  This method runs the program by first asking the user
    //                for the file names of the inventory and cashiers. It then
    //                shows a menu for the user from where they can choose an option.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    
    public void runProgram() {

    	Scanner scanner = new Scanner(System.in);

        // Ask the user for the inventory file name
        String inventoryFileName = null;
        File inventoryFile = null;
        try {
            do {
                System.out.print("Enter the inventory file name: ");
                inventoryFileName = "C:\\Java\\" + scanner.nextLine() + ".txt";
                inventoryFile = new File(inventoryFileName);
                if (!inventoryFile.exists()) {
                    System.out.println("Error: The specified inventory file does not exist. Please try again.");
                }
            } while (!inventoryFile.exists());
        } catch (Exception e) {
            System.err.println("An error occurred while processing the inventory file: " + e.getMessage());
            // Optionally, you can log the exception or take other appropriate action
        }

        // Define the cashier file name here or retrieve it from wherever you need
        String cashierFileName;
        File cashierFile;
        do {
            System.out.print("Enter the cashier file name: ");
            cashierFileName = "C:\\Java\\" + scanner.nextLine() + ".txt";
            cashierFile = new File(cashierFileName);
            if (!cashierFile.exists()) {
                System.out.println("Error: The specified cashier file does not exist. Please try again.");
            }
        } while (!cashierFile.exists());

        List<StoreItem> storeItems = readInventoryFile(inventoryFileName);
        CashRegister cashRegister = new CashRegister(cashierFileName); // Pass the cashier file name

        while (true) {
            displayMenu(storeItems);
            System.out.print("Select an option: ");
            String input = scanner.nextLine();

            if ("-1".equals(input)) {
                System.out.println("Exiting program.");
                scanner.close();
                System.exit(0);
            }

            boolean itemSelected = false;
            for (StoreItem item : storeItems) {
                if (item.getItemDescription().equalsIgnoreCase(input)) {
                    selectItemsForPurchase(storeItems, cashRegister, input); // Pass the item description
                    itemSelected = true;
                    break;
                }
            }

            if (!itemSelected) {
                switch (input) {
                    case "1":
                        showInventory(storeItems);
                        break;
                    case "2":
                        cashRegister.showItems();
                        break;
                    case "3":
                        clearCashRegister(cashRegister, storeItems);
                        break;
                    case "4":
                        cashRegister.checkOut(scanner);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    
    /*public List<StoreItem> getSelectedItems() {
    	List<StoreItem> selectedItems = new ArrayList<>();

		return selectedItems;
    }*/


    //***************************************************************
    //
    //  Method:      readInventoryFile 
    // 
    //  Description:  This method reads the inventory file and stores the data
    //                in a ArrayList.
    //
    //  Parameters:   String fileName
    //
    //  Returns:      Lists toreItems
    //
    //**************************************************************
    
    public List<StoreItem> readInventoryFile(String fileName) {
        List<StoreItem> storeItems = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+"); // Split by one or more spaces
                if (parts.length == 4) {
                    int itemNumber = Integer.parseInt(parts[0]);
                    String itemDescription = parts[1];
                    int unitsInInventory = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    storeItems.add(new StoreItem(itemNumber, itemDescription, unitsInInventory, price));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the inventory file: " + e.getMessage());
            // Optionally, you can log the exception or take other appropriate action
        }
        return storeItems;
    }


    //***************************************************************
    //
    //  Method:       displayMenu
    // 
    //  Description:  This method builds the menu for the user
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************

    public void displayMenu(List<StoreItem> storeItems) {
        System.out.println("\nMenu:");
        
        // Generate menu options based on item descriptions (second column of storeItems)
        for (StoreItem item : storeItems) {
        	System.out.println(item.getItemDescription());
        }

        // Add other menu options
        System.out.println("1. Show Inventory");
        System.out.println("2. Show Cash Register");
        System.out.println("3. Clear Cash Register");
        System.out.println("4. Check Out");
        System.out.println("-1. Exit");
    }

    //***************************************************************
    //
    //  Method:     showInventory
    // 
    //  Description:  This method shows the available inventory to the user 
    //
    //  Parameters:   List<StoreItem> storeItems
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    
    public void showInventory(List<StoreItem> storeItems) {
        System.out.println("\nInventory:");
        for (StoreItem item : storeItems) {
            System.out.println("No." + item.getItemNumber() + " - " + item.getItemDescription() + " - Price: $" + item.getPrice()
                    + " - Units in Inventory: " + item.getUnitsInInventory());
        }
    }

    //***************************************************************
    //
    //  Method:     clearCashRegister  
    // 
    //  Description:  This method clears the register from any items chosen
    //
    //  Parameters:   CashRegister cashRegister, List storeItems
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    
    public void clearCashRegister(CashRegister cashRegister, List<StoreItem> storeItems) {
        List<StoreItem> purchasedItems = cashRegister.getItems(); // Get the items in the cash register

        for (StoreItem purchasedItem : purchasedItems) {
            // Find the corresponding item in the storeItems list
            StoreItem storeItem = storeItems.stream()
                    .filter(item -> item.getItemNumber() == purchasedItem.getItemNumber())
                    .findFirst()
                    .orElse(null);

            if (storeItem != null) {
                // Increase the inventory count by the quantity of items in the cash register
                storeItem.increaseInventory(1); // Increase by 1 for each item in the cash register
            }
        }

        // Clear the items in the cash register
        cashRegister.clearRegister();
        System.out.println("Cleared the cash register. Items returned to inventory.");
    }





    //***************************************************************
    //
    //  Method:       selectItemsForPurchase
    // 
    //  Description:  This method stores the input from the user on what
    //                items they want to select.
    //
    //  Parameters:   List<StoreItem> storeItems, CashRegister cashRegister, Scanner scanner
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    
    public void selectItemsForPurchase(List<StoreItem> storeItems, CashRegister cashRegister, String itemDescription) {
        StoreItem selectedItem = findItemByDescription(storeItems, itemDescription);

        if (selectedItem != null) {
            if (selectedItem.getUnitsInInventory() > 0) {
                selectedItem.decreaseInventory(); // Update the inventory first
                cashRegister.purchaseItem(selectedItem); // Then add to cashRegister
                System.out.println("Item added to the Cash Register.");
            } else {
                System.out.println(selectedItem.getItemDescription() + " is out of stock.");
            }
        } else {
            System.out.println("Item not found in inventory. Please enter a valid item description.");
        }
    }



    private StoreItem findItemByDescription(List<StoreItem> storeItems, String itemDescription) {
        for (StoreItem item : storeItems) {
            if (item.getItemDescription().equalsIgnoreCase(itemDescription)) {
                return item;
            }
        }
        return null;
    }
    
  //***************************************************************
    //
    //  Method:       developerInfo (Non Static)
    // 
    //  Description:  The developer information method of the program
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void developerInfo()
    {
       System.out.println("Name:    Christian Garcia");
       System.out.println("Course:  COSC 4301 Modern Programming");
       System.out.println("Project: Three\n\n");

    } // End of the developerInfo method

}


