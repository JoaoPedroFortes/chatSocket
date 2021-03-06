


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

public class ServerTCP {

    private ArrayList<Socket> clientes;


    private Map<String, Socket> map;
    private int porta;

    public ServerTCP(int porta) {
        this.map = new HashMap<String, Socket>();
        this.clientes = new ArrayList<Socket>();
        this.porta = porta;

    }

    public void exec(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(this.porta);
        System.out.println(server);


        while (true) {
            Socket cliente = server.accept();


            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            Obj nome = (Obj) ois.readObject();

            if (map.containsKey(nome.y)) {
                System.out.println("nome ja existe");

                cliente.getOutputStream().write("404\0".getBytes());
                cliente.getOutputStream().flush();
                cliente.close();
                System.out.println(cliente.isClosed());
                System.out.println(clientes.toString());
                continue;
            }
            cliente.getOutputStream().write("200\0".getBytes());
            cliente.getOutputStream().flush();

            this.clientes.add(cliente);
            map.put(nome.y, cliente);
            System.out.println(clientes.toString());
            ChatServer cs = new ChatServer(cliente, this, nome.y);
            System.out.println();
            new Thread(cs).start();


        }

    }

    public void distribuiMensagem(Socket clienteQueEnviou, String msg) throws IOException, ClassNotFoundException {
        if (msg.contains("//")) {
            distribuiParaUm(clienteQueEnviou, msg);
        } else {
            for (Socket cliente : this.clientes) {

                if (!cliente.equals(clienteQueEnviou)) {
                    try {
                        PrintStream ps = new PrintStream(cliente.getOutputStream());
                        ps.println(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private void distribuiParaUm(Socket clienteQueEnviou, String msg) throws IOException, ClassNotFoundException {
        String[] array = new String[1];
        array = msg.split("//");

        for (String nome : map.keySet()) {
            if (nome.equals(array[1])) {
                Socket cliente = map.get(nome);
                if (!cliente.equals(clienteQueEnviou)) {
                    try {
                        PrintStream ps = new PrintStream(cliente.getOutputStream());
                        ps.println("MSG PRIVADA DE " + array[0].toUpperCase() + ":" + array[2]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public static void main(String[] args) throws Exception {
        try {

            new ServerTCP(8080).exec(args);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
