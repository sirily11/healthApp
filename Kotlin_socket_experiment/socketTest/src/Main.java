import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        try
        {
            client c = new client("127.0.0.1", 5555);
            while (true)
            {
                Scanner scan = new Scanner(System.in);
                String in = scan.nextLine();
                c.sendMsg(in);
                if(in.equals("out")){
                    c.closeSocket();
                    break;
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
