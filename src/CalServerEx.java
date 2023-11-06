import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class CalServerEx {

    private static final int THREAD_POOL_SIZE = 10;

    public static String calc(String exp) {
        StringTokenizer st = new StringTokenizer(exp, " ");
        String res = " ";
        if (st.countTokens() >= 3)
            {
                res = "error - There's a lot of operands";
                // ERR_TooManyOP
                return res;
            }
        else if (st.countTokens() < 3)
        {
            res = "error - There's fewer operands.";
            // ERR_TooFewerOP
            return res;
        }
        ///String res = "";
        int op1 = Integer.parseInt(st.nextToken());
        String opcode = st.nextToken();
        int op2 = Integer.parseInt(st.nextToken());
        switch (opcode) {
            case "+":
                res = Integer.toString(op1 + op2);
                break;
            case "-":
                res = Integer.toString(op1 - op2);
                break;
            case "*":
                res = Integer.toString(op1 * op2);
                break;
            case "/":
                if (op2 == 0) {
                    res = "error - Divided by 0";
                    // ERR_DividedBy0
                    break;
                }
                else {
                res = Integer.toString(op1 / op2);
                break;
                }

            default:
                res = "error - Please write proper operator";
                // ERR_NotProperOP
        }
        return res;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        ServerSocket listener = null;

        try {
            Serverconfig config = new Serverconfig();
            listener = new ServerSocket(config.getPort());
            System.out.println("Waiting for Connect....");

            while (true) {
                Socket socket = listener.accept();
                Runnable worker = new ClientHandler(socket);
                executorService.execute(worker);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (listener != null)
                    listener.close();
            } catch (IOException e) {
                System.out.println("Error shutting down server");
            }
            executorService.shutdown();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("You are connected..");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                String inputMessage;

                while ((inputMessage = in.readLine()) != null) {
                    if ("bye".equalsIgnoreCase(inputMessage)) {
                        System.out.println("Client terminated the connection");
                        break;
                    }
                    System.out.println(inputMessage);
                    String res = calc(inputMessage);
                    out.write(res + "\n");
                    out.flush();
                }
            } catch (IOException e) {
                System.out.println("Error chatting with client: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error closing socket: " + e.getMessage());
                }
            }
        }
    }
}
