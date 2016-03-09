package com.dam.dani;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{
    private int port;
    private ServerSocket socket;

    private DataBase db;
    private ArrayList<ClientConnection> connectionList;
    private ArrayList<User> userList;
    private ArrayList<String> portList;

    public Server(int port)
    {
        this.port = port;
        this.db = new DataBase();

        connectionList = new ArrayList<>();
        userList = new ArrayList<>();
        portList = new ArrayList<>();
    }

    public void connect() throws IOException
    {
        socket = new ServerSocket(this.port);
    }

    public void disconnect() throws IOException
    {
        socket.close();
    }

    public void removeClient(ClientConnection cc) throws IOException
    {
        cc.getSocket().close();
        connectionList.remove(cc);
    }

    public boolean isConnected()
    {
        return !socket.isClosed();
    }

    public Socket listen() throws IOException
    {
        return socket.accept();
    }

    public void createConnection(ClientConnection cc)
    {
        this.connectionList.add(cc);
    }

    public ArrayList<ClientConnection> getConnectionList()
    {
        return this.connectionList;
    }

    public ArrayList<User> getUserList()
    {
        return this.userList;
    }

    public void newUser(String userName, String pass)
    {
        User user = new User(userName, pass);
        userList.add(user);
    }

    public String getUserString(String userName)
    {
        String user = "";
        for(int i=0; i<this.userList.size(); i++)
        {
            if(!userList.get(i).getName().equals(userName))
                if( i == userList.size() - 1)
                    user = user + this.userList.get(i).getName();
            else
                    user = user + this.userList.get(i).getName() + ", ";
        }
        System.out.println(user);
        return user;
    }

    public void addPort(String ip)
    {
        this.portList.add(ip);
    }

    public boolean checkPort(String ip)
    {
        for(int i=0; i<portList.size(); i++)
        {
            if(portList.get(i).equals(ip))
                return true;
        }
        return false;
    }

    public void removeIp(String ip)
    {
        this.portList.remove(ip);
    }

    public void message(ClientConnection cc, String[] options)
    {
        for(int i=0; i<this.connectionList.size(); i++)
        {
            String name = options[1].split(", ")[1];
            if(this.connectionList.get(i).getUser().getName().equals(name))
            {
                String message = "message-";
                message += cc.getUser().getName() + ", " + options[1].split(", ")[0];
                this.connectionList.get(i).getSalida().println(message);
                break;
            }
        }
    }

    public void loadListUsers(DataBase db)
    {
        for(int i=0; i<this.connectionList.size(); i++)
        {
            String usuarios = db.getUsers(this.connectionList.get(i).getUser().getName());
            String message = "users-";
            this.connectionList.get(i).getSalida().println(message + usuarios);
        }
    }
}
