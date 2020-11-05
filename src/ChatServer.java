

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer implements Runnable {

    private Socket cliente;
    private ServerTCP server;

    public ChatServer(Socket cliente, ServerTCP server) {
        this.cliente = cliente;
        this.server = server;
    }

    @Override
    public void run() {

        try(Scanner s = new Scanner(this.cliente.getInputStream())) {
            while (s.hasNextLine()) {
                server.distribuiMensagem(cliente,s.nextLine());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
