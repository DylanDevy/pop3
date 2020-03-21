package pop.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 110);
            Thread t1 = new Thread(() -> write(socket));
            t1.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (t1.isAlive()) {
                System.out.println(bufferedReader.readLine());
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void write(Socket socket) {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader systemInBufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while (true) {
                message = systemInBufferedReader.readLine();
                if (message.equals("QUIT")) {
                    break;
                }
                printWriter.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
