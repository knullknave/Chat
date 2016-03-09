package com.dam.dani;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

import static com.dam.dani.Constants.*;

public class Main
{
    public static int connections;

    public static void main(String[] args)
    {
        Server server = new Server(5555);
        ClientConnection cc = null;

        try
        {
            server.connect();
            while(server.isConnected())
            {
                System.out.println("Server connected");
                Socket socket = server.listen();
                //connections = server.getConnections(socket);

                if(!server.checkPort(socket.getInetAddress().getHostAddress()))
                {
                    server.addPort(socket.getInetAddress().getHostAddress());
                    cc = new ClientConnection(socket, server);
                    server.createConnection(cc);
                    Thread t = new Thread(cc);
                    t.start();
                }
                else
                {
                    System.out.println("A user with this ip is already connected");
                }
            }
        }
        catch(IOException ioex)
        {
            try
            {
                server.disconnect();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            ioex.printStackTrace();
        }
    }
}
