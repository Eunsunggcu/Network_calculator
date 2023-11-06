import java.io.*;
import java.net.*;

public class CalClientEx {
    public static void main(String[] args) {
        Serverconfig config = new Serverconfig();
        try (Socket socket = new Socket(config.getHost(), config.getPort());
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            System.out.println("서버에 연결되었습니다. 계산식을 입력하세요 (예: 2 + 2)");

            String userInput;
            while ((userInput = consoleReader.readLine()) != null && !userInput.equalsIgnoreCase("bye")) {
                out.write(userInput + "\n");
                out.flush();
                String response = in.readLine();
                System.out.println("서버의 응답: " + response);
            }

        } catch (UnknownHostException e) {
            System.err.println("호스트를 찾을 수 없습니다: " + config.getHost());
        } catch (IOException e) {
            System.err.println("I/O 에러: " + e.getMessage());
        }
    }
}
