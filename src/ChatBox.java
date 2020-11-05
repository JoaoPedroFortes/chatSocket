import java.io.InputStream;
import java.util.Scanner;


public class ChatBox implements Runnable {
    private InputStream server;

    private String nome;
    public ChatBox(InputStream server,String nome ) {
        this.server = server;
        this.nome= nome;
    }

    @Override
    public void run() {
        try (Scanner s = new Scanner(this.server)) {
            while (s.hasNextLine()) {
                System.out.println(nome+": "+ s.nextLine());
            }
        }

    }
}


