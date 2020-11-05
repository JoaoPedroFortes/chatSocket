
import com.sun.media.jfxmediaimpl.HostUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class ServerTCP {

    private ArrayList<Socket> clientes;
    private int porta;
    public ServerTCP(int porta) {
        this.clientes = new ArrayList<Socket>();
        this.porta = porta;
    }

    public  void exec(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(this.porta);
        System.out.println(server);


        while (true){
            Socket cliente = server.accept();
            this.clientes.add(cliente);


            System.out.println(clientes.toString());
            ChatServer cs = new ChatServer(cliente,this);
            new Thread(cs).start();
        }

    }
    public void distribuiMensagem(Socket clienteQueEnviou, String msg) {
        for (Socket cliente : this.clientes) {
            if(!cliente.equals(clienteQueEnviou)){
                try {
                    PrintStream ps = new PrintStream(cliente.getOutputStream());
                    ps.println(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        try{
        new ServerTCP(8080).exec(args);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
