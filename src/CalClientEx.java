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
                System.out.println("Response of Server: " + response);
            }

        } catch (UnknownHostException e) {
            System.err.println("Host not found: " + config.getHost());
        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
        }
    }
}
