import java.io.*;
import java.net.*;
import java.util.Scanner;
public class ChatClient {
   private static final String SERVER_IP = "127.0.0.1"; // localhost
   private static final int SERVER_PORT = 1234;
   public static void main(String[] args) {
       try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {
           System.out.println("Connected to the chat server");
           // Thread to read messages from server
           new Thread(new ReadHandler(socket)).start();
           // Main thread to send messages
           PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
           @SuppressWarnings("resource")
           Scanner scanner = new Scanner(System.in);
           while (true) {
               String message = scanner.nextLine();
               out.println(message);
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}
class ReadHandler implements Runnable {
   private BufferedReader in;
   public ReadHandler(Socket socket) throws IOException {
       in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
   }
   public void run() {
       String response;
       try {
           while ((response = in.readLine()) != null) {
               System.out.println(response);
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}