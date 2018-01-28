import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class client
{
    // initialize socket and input output streams
    private DataInputStream input = null;
    private Socket socket = null;
    private DataOutputStream osw;

    // constructor to put ip address and port
    public client(String address, int port) throws IOException
    {
        try {
            socket = new Socket(address, port);
            osw = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e)
        {
            System.err.print(e);
        }
    }
    public void sendMsg(String msg) throws IOException
    {
        osw.writeUTF(msg);
        System.out.println("Send msg " + msg);

    }
    public void closeSocket() throws IOException
    {
        socket.close();
    }
}
