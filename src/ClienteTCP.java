

import jdk.nashorn.internal.objects.annotations.Getter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.DigestInputStream;
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



        Socket cliente = new Socket("localhost", 8080);
      //  Socket cliente = new Socket("45.6.108.105", 8080);

        ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
        Obj obj = new Obj();
        obj.y=this.getNome();
        oos.writeObject(obj);

        System.out.println(cliente);

        Scanner msg = new Scanner(System.in);
        PrintStream saida = new PrintStream(cliente.getOutputStream());

        ChatBox chat = new ChatBox(cliente.getInputStream());
        new Thread(chat).start();

        while (msg.hasNextLine()) {
            saida.println(this.getNome()+": "+ msg.nextLine());
        }


    }

    public static void main(String[] args) throws Exception {
        System.out.println("Digite seu nome:");
        Scanner sc = new Scanner(System.in);
        new ClienteTCP(sc.nextLine()).exec(args);
    }
}
