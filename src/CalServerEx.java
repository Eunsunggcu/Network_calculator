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
                return res;
            }
        else if (st.countTokens() < 3)
        {
            res = "error - There's fewer operands.";
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
                    break;
                }
                else {
                res = Integer.toString(op1 / op2);
                break;
                }

            default:
                res = "error - Please write proper operator";
        }
        return res;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        ServerSocket listener = null;

        try {
            Serverconfig config = new Serverconfig();
            listener = new ServerSocket(config.getPort());
            System.out.println("연결을 기다리고 있습니다....");

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
                System.out.println("서버 종료 중 오류 발생");
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
            System.out.println("연결 되었습니다.");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                String inputMessage;

                while ((inputMessage = in.readLine()) != null) {
                    if ("bye".equalsIgnoreCase(inputMessage)) {
                        System.out.println("클라이언트에서 연결을 종료하였음");
                        break;
                    }
                    System.out.println(inputMessage);
                    String res = calc(inputMessage);
                    out.write(res + "\n");
                    out.flush();
                }
            } catch (IOException e) {
                System.out.println("클라이언트와 채팅 중 오류 발생: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("소켓을 닫는 중 오류 발생: " + e.getMessage());
                }
            }
        }
    }
}
