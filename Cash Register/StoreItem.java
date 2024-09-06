New! Keyboard shortcuts … Drive keyboard shortcuts have been updated to give you first-letters navigation
//********************************************************************
//
//  Developer:     Christian Garcia
//
//  Project #:     Three
//
//  File Name:     StoreItem.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      09/22/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  Java 1.8
//
//  Description:   This java class contains all the getters and methods used
//                 to create and maintain the dynamic inventory list.
//
//********************************************************************

public class StoreItem {
    private int itemNumber;
    private String itemDescription;
    private int unitsInInventory;
    private double price;
    private int unitsSold;

    public StoreItem(int itemNumber, String itemDescription, int unitsInInventory, double price) {
        this.itemNumber = itemNumber;
        this.itemDescription = itemDescription;
        this.unitsInInventory = unitsInInventory;
        this.price = price;
        this.unitsSold = 0;
    }

    //Gets the item number from the list
    public int getItemNumber() {
        return itemNumber;
    }

    //gets the item name from the list
    public String getItemDescription() {
        return itemDescription;
    }

    //gets the units available for the items from the list
    public int getUnitsInInventory() {
        return unitsInInventory;
    }

    //gets the prices for the items from the list
    public double getPrice() {
        return price;
    }
 
    //decreases the inventory, making the inventory 'dynamic'
    public void decreaseInventory() {
        if (unitsInInventory > 0) {
            unitsInInventory--;
            unitsSold++; // Increment unitsSold
        }
    }

    //increases the inventory whenever an order is canceled
    public void increaseInventory(int unitsToAdd) {
        if (unitsToAdd > 0) {
            this.unitsInInventory += unitsToAdd;
        }
    }
 
  
}



