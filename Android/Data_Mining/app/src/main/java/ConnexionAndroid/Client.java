package ConnexionAndroid;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Doublon on 23/10/2017.
 */

public class Client
{
    private Socket cliSock=null;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private InetAddress IP ;

    Client()
    {
        try
        {
            setIP(InetAddress.getByName("127.0.0.1"));
            setCliSock(new Socket(getIP(),50000));
            setDis(new DataInputStream(cliSock.getInputStream()));
            setDos(new DataOutputStream(cliSock.getOutputStream()));
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setCliSock(Socket cliSock)
    {
        this.cliSock = cliSock;
    }

    public void setDis(DataInputStream dis)
    {
        this.dis = dis;
    }

    public void setDos(DataOutputStream dos)
    {
        this.dos = dos;
    }

    public void setIP(InetAddress IP)
    {
        this.IP = IP;
    }

    public Socket getCliSock()
    {
        return cliSock;
    }

    public DataInputStream getDis()
    {
        return dis;
    }

    public DataOutputStream getDos()
    {
        return dos;
    }

    public InetAddress getIP()
    {
        return IP;
    }
}
