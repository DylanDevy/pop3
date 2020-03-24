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
            Thread writeThread = new Thread(() -> write(socket));
            writeThread.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (writeThread.isAlive()) {
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
            do {
                message = systemInBufferedReader.readLine().trim();
                printWriter.println(message);
            } while (!message.toUpperCase().equals("QUIT"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
