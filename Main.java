import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    ArrayList<Client> clients = new ArrayList<>();
    ServerSocket server;
    Main() throws IOException {
        // создаем серверный сокет на порту 1234
        server = new ServerSocket(1234);
    }

    void sendAll (String message) {
        for (Client client : clients) {
            client.receive(message);
        }
    }
    public void run() {
        while (true) {
            System.out.println("Waiting...");
            try {
                // ждем клиента из сети// ждем клиента из сети
                Socket socket = server.accept();
                System.out.println("Client connected!");
                // создаем клиента на своей стороне
                clients.add(new Client(socket, this));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        new Main().run();


    }
}



