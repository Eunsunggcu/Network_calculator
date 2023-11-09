import java.io.*;
import java.net.*;

public class CalClientEx {

    public static void main(String[] args) {
        Serverconfig config = new Serverconfig();
        try (Socket socket = new Socket(config.getHost(), config.getPort());
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
    
            System.out.println("Connected to the server. Enter a calculation formula (e.g. 2 + 2)");
    
            String userInput;
            while ((userInput = consoleReader.readLine()) != null && !userInput.equalsIgnoreCase("bye")) {
                out.write(userInput + "\n");
                out.flush();
                String response = in.readLine();
                interpretResponse(response);
            }
    
        } catch (UnknownHostException e) {
            System.err.println("Host not found: " + config.getHost());
        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
        }
    }
    
    private static void interpretResponse(String response) {
        if (response.startsWith("ERR_")) {
            // Handle error codes
            switch (response) {
                case "ERR_001":
                    System.out.println("Error: Too many operands.");
                    break;
                case "ERR_002":
                    System.out.println("Error: Not enough operands.");
                    break;
                case "ERR_003":
                    System.out.println("Error: Division by zero.");
                    break;
                case "ERR_004":
                    System.out.println("Error: Unknown operator.");
                    break;
                default:
                    System.out.println("Error: Unknown error code.");
                    break;
            }
        } else if (response.startsWith("ANS_")) {
            // Display the answer
            System.out.println("Answer = " + response.substring(4));
        } else {
            System.out.println("Unknown response");
        }
    }
    
}
