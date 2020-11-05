import java.io.InputStream;
import java.util.Scanner;


public class ChatBox implements Runnable {
    private InputStream server;

    private String nomeCli;
    public ChatBox(InputStream server, String nomeCli) {
        this.server = server;
        this.nomeCli= nomeCli;
    }

    @Override
    public void run() {
        try (Scanner s = new Scanner(this.server)) {
            while (s.hasNextLine()) {
                System.out.println(s.nextLine());
            }
        }

    }
}


