import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class EchoClient {
    public static void main(String[] args) {
        try {
            try (Socket socket = new Socket("localhost", 2000);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
                String line;
                while ((line = console.readLine()) != null) {
                    out.println(line);
                    System.out.println("Received: " + in.readLine());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
    
}
