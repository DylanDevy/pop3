package pop.service;

import pop.entity.Session;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(110);
            System.out.println("<><<>>< POP3 SERVER IS READY ><<>><>");
            PopSessionThread.Builder popSessionThreadBuilder = BaseSessionBuilder.getPopSessionThreadBuilder();
            while (true) {
                popSessionThreadBuilder
                        .setSocket(serverSocket.accept())
                        .setSession(new Session())
                        .build()
                        .start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
