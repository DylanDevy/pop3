package pop.service;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.keyStore", "server.key.store");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        System.setProperty("javax.net.ssl.trustStore", "client.key.store");

        try {
            ServerSocket serverSocket = SSLServerSocketFactory.getDefault().createServerSocket(110);
            System.out.println("<<SERVER READY>>");
            while (true) {
                new PopSessionThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
