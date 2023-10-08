import java.io.*;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable {
    Socket socket;

    Scanner in;
    PrintStream out;
    private PrintWriter outMessage;
    // входящее собщение
    private Scanner inMessage;
    Main server;
    private static int clients_count = 0;

    public Client(Socket socket, Main server){

        try {
            clients_count++;
            this.server = server;
            this.socket = socket;
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // запускаем поток
        new Thread(this).start();
    }
    void receive (String message) {
        out.println(message);
    }



    public void run() {
        try {

            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            in = new Scanner(is);
            out = new PrintStream(os);


            // читаем из сети и пишем в сеть
            out.println("Welcome to chat!");
            while (true) {
                // сервер отправляет сообщение
                server.sendAll("Новый участник вошёл в чат!");
                server.sendAll("Клиентов в чате = " + clients_count);
                break;
            }
            String input = in.nextLine();
            while (!input.equals("bye")) {
                server.sendAll(input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
