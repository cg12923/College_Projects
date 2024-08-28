//********************************************************************
//
//  Developer:     Christian Garcia
//
//  Project #:     4
//
//  File Name:     Server.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      9/29/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  Java 1.8
//
//  Description:   Server class, it receives input from the client and process
//                 it. In this case it takes the integers and finds the sum, mean
//                 and Deviation of the even or odd numbers. It then creates a response
//                 to send back to the client and it continues listening for any other
//                 messages.
//
//********************************************************************

import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {
        Server server = new Server();
        server.developerInfo();
        server.startConnection(4301);
    }
    
    
    //***************************************************************
    //
    //  Method:  ServerSocket     
    // 
    //  Description:  Declaring the serverSocket as a private instance so
    //                that it can be accessed through out the class
    //
    //  Parameters:   N/A
    //
    //  Returns:      N/A
    //
    //**************************************************************
	
    private ServerSocket serverSocket;

    
    //***************************************************************
    //
    //  Method:   startConnection 
    // 
    //  Description:  Starts the connection to the Client and takes on a parameter
    //                of integer portNum which provides the port number to connect.
    //
    //  Parameters:   Integer portNum
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public void startConnection(int portNum) {
    	try {
    		
    		// Create a new ServerSocket on the specified port
            serverSocket = new ServerSocket(portNum);
            System.out.println("Server started on port " + portNum);
            
            
            //while loop to accept multiple client connections
            while (true) {
            	
            	//holding until client accepts the connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                
                
                //Calling a method to handle 
                handleClientConnection(clientSocket);
            }
        } 
    	
    	// Catch any IOExceptions that occur during socket creation or connection acceptance
    	catch (IOException e) 
    	{
    		System.err.println("Server error: " + e.getMessage());
        }
    	
    }//end of startConnection method

    
    //***************************************************************
    //
    //  Method:   handleClientConnection 
    // 
    //  Description:  Handles the client connection and receives the input from
    //                the client and echos it back.
    //
    //  Parameters:  private instance Socket clientSocket
    //
    //  Returns:      N/A
    //
    //**************************************************************
    private void handleClientConnection(Socket clientSocket) throws IOException {
        try (
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received message from client: " + inputLine);

                if (inputLine.equals("bye")) {
                    // If the client sends "bye", break out of the loop to disconnect
                    break;
                }

                String[] inputValues = inputLine.split(" ");
                if (inputValues.length != 3) {
                    out.println("Error: Invalid input. Please provide three integers separated by spaces.");
                    continue;
                }

                int num1 = Integer.parseInt(inputValues[0]);
                int num2 = Integer.parseInt(inputValues[1]);
                int operation = Integer.parseInt(inputValues[2]);

                if (num1 >= num2 || (operation != 1 && operation != 2) || num1 <= 0 || num2 <= 0) {
                    out.println("Error: Invalid input. Please check the input values and verify they follow the guidelines: \n"
                    		+ "1. The first integer must be less than the second.\r\n"
                    		+ "2. The third integer must be 1 or 2\r\n"
                    		+ "3. All the integers must be greater than zero.");
                } else {
                    int sum = 0;
                    int count = 0;
                    for (int i = num1; i <= num2; i++) {
                        if ((operation == 1 && i % 2 == 1) || (operation == 2 && i % 2 == 0)) {
                            sum += i;
                            count++;
                        }
                    }

                    double mean = (double) sum / count;

                    double stdDev = 0.0;
                    for (int i = num1; i <= num2; i++) {
                        if ((operation == 1 && i % 2 == 1) || (operation == 2 && i % 2 == 0)) {
                            stdDev += Math.pow(i - mean, 2);
                        }
                    }
                    stdDev = Math.sqrt(stdDev / count);

                 // Instead of sending responses individually, send them as a single string
                    String response = "Sum: " + sum + "\nMean: " + mean + "\nStandard Deviation: " + stdDev;
                    out.println(response);

                }
            }
        } catch (IOException e) {
            System.err.println("Error handling client connection: " + e.getMessage());
        }
    }



    //***************************************************************
    //
    //  Method:   stop
    // 
    //  Description:  Closes the connection when prompted to
    //                
    //
    //  Parameters:   N/A
    //
    //  Returns:      N/A
    //
    //**************************************************************
    
    public void stop() throws IOException {
        serverSocket.close();
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
        System.out.println("Project: Four\n\n");
    }
}
