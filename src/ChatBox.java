import java.io.InputStream;
import java.util.Scanner;


public class ChatBox implements Runnable {
    private InputStream server;


    public ChatBox(InputStream server ) {
        this.server = server;

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


