/*
 * @Author: aree whr_zm@163.com
 * @Date: 2025-01-14 23:24:14
 * @LastEditors: aree whr_zm@163.com
 * @LastEditTime: 2025-01-14 23:25:29
 * @FilePath: \Java\EchoServer.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        try {
            try (ServerSocket server = new ServerSocket(2000)) {
                System.out.println("Server started");
                while (true) {
                    Socket client = server.accept();
                    System.out.println("Client connected");
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println("Received: " + line);
                        out.println(line);
                    }
                    System.out.println("Client disconnected");
                    client.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}