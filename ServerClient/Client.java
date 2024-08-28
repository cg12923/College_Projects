//********************************************************************
//
//  Developer:     Christian Garcia
//
//  Project #:     4
//
//  File Name:     Client.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      9/29/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  Java 1.8
//
//  Description:   Client class, it creates the client side of the client/server
//                 and sends messages to the server and receives the response from
//                 it as well.
//
//********************************************************************

import java.io.*;
import java.net.*;

public class Client {
    private Socket clientSocket;

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 4301);
        client.sendIntegers();
        client.stopConnection();
    }
    
    //***************************************************************
    //
    //  Method:   startConnection 
    // 
    //  Description:  Creates a connection to the server with the IP address
	//                and port number
    //                
    //  Parameters: String ip, Integer port
    //
    //  Returns:    N/A
    //
    //**************************************************************

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            System.out.println("Connected to server on port " + port);
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }
    
    //***************************************************************
    //
    //  Method:   sendIntegers
    // 
    //  Description:  Sends the Integers to the connected server and prints
	//                the response.
    //                
    //  Parameters: N/A
    //
    //  Returns:    N/A
    //
    //**************************************************************

    public void sendIntegers() {
        try (
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
     
        	while (true) {
        	    System.out.println("Enter three integers separated by spaces (e.g., 1 10 1) or 'bye' to exit: ");
        	    String userInput = stdIn.readLine();

        	    // If the user enters "bye," exit the loop
        	    if (userInput.equalsIgnoreCase("bye")) {
        	        System.out.println("End");
        	        break;
        	    }

        	    // Send the input as a single line to the server
        	    out.println(userInput);

        	    String response;
        	    while ((response = in.readLine()) != null) {
        	        if (response.startsWith("Error:")) {
        	            System.err.println("Server Error: " + response.substring(7)); // Print error message (excluding "Error:")
        	        } else {
        	            System.out.println("Server Response: " + response);
        	        }

        	        // Check if there are more responses for the current input
        	        if (!in.ready()) {
        	            break;
        	        }
        	    }
        	}
        }
        

            
            // Catch any IOExceptions that occur during the connection
            catch (IOException e) 
            {
                System.err.println("Error sending/receiving message: " + e.getMessage());
            }
    	}
    
    
    //***************************************************************
    //
    //  Method:   stopConnection
    // 
    //  Description:  Stops the connection between server and client and 
	//                informs client that connection has been closed
    //                
    //  Parameters: N/A
    //
    //  Returns:    N/A
    //
    //**************************************************************
    public void stopConnection() throws IOException {
        clientSocket.close();
        System.out.println("Connection closed");
    }
}
