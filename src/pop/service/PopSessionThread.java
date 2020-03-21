package pop.service;

import pop.entity.Session;
import pop.service.popcommand.PopCommandExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PopSessionThread extends Thread {
    private Socket socket;
    private Session session;
    private PopCommandExecutor popCommandExecutor;

    private PopSessionThread(Socket socket, Session session, PopCommandExecutor popCommandExecutor) {
        this.socket = socket;
        this.session = session;
        this.popCommandExecutor = popCommandExecutor;
    }

    public void run() {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            printWriter.println("+OK POP3 server is waiting for your commands");
            while (true) {
                printWriter.println(popCommandExecutor.execute(bufferedReader.readLine(), session));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Builder {
        private Socket socket;
        private Session session;
        private PopCommandExecutor popCommandExecutor;

        public PopSessionThread.Builder setSocket(Socket socket) {
            this.socket = socket;

            return this;
        }
        public PopSessionThread.Builder setSession(Session session) {
            this.session = session;

            return this;
        }

        public PopSessionThread.Builder setPopCommandExecutor(PopCommandExecutor popCommandExecutor) {
            this.popCommandExecutor = popCommandExecutor;

            return this;
        }

        public PopSessionThread build() {
            return new PopSessionThread(socket, session, popCommandExecutor);
        }
    }
}
