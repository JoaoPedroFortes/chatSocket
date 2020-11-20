


import jdk.nashorn.internal.objects.annotations.Getter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ClienteTCP {

    private String nome;

    public ClienteTCP(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void exec(String[] args) throws Exception {

        try {
           Socket cliente = new Socket("localhost", 8080);
       // Socket cliente = new Socket("45.6.108.105", 8081);

            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            Obj obj = new Obj();
            obj.y = this.getNome();
            oos.writeObject(obj);

            byte[] msgServer = new byte[3];
            cliente.getInputStream().read(msgServer, 0, 3);

            String sMsg = new String(msgServer, StandardCharsets.UTF_8);
            if (sMsg.equals("404")) {
                System.out.println("Nick já existe!");
                cliente.close();
                return;
            }
            System.out.println("########### Bem-vindo(a) ao chat! #############");
            System.out.println("Para mandar uma mensagem privada digite: //NICK DO DESTINO// mensagem");


            Scanner msg = new Scanner(System.in);
            PrintStream saida = new PrintStream(cliente.getOutputStream());

            ChatBox chat = new ChatBox(cliente.getInputStream());
            new Thread(chat).start();


            while (msg.hasNextLine()) {
                saida.println(this.getNome() + ": " + msg.nextLine());

            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void main(String[] args) throws Exception {
        System.out.println("Digite seu Nick:");
        Scanner sc = new Scanner(System.in);
        new ClienteTCP(sc.nextLine()).exec(args);
    }
}
