package com.dam.dani;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;

public class ClientConnection implements Runnable
{
    private Socket socket;
    private Server server;
    private PrintWriter salida;
    private BufferedReader entrada;

    private User user;
    private DataBase db;

    public ClientConnection(Socket socket, Server server) throws IOException
    {
        this.socket = socket;
        this.server = server;

        salida = new PrintWriter(socket.getOutputStream(), true);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        this.db = new DataBase();

        user = new User();
    }

    public Socket getSocket()
    {
        return this.socket;
    }

    public User getUser()
    {
        return  this.user;
    }

    public PrintWriter getSalida()
    {
        return this.salida;
    }

    @Override
    public void run()
    {
        String userName = "";
        String pass = "";

        try
        {
            String line = null;
            String[] options;
            while ((line = entrada.readLine()) != null)
            {
                options = line.split("-");

                switch (options[0])
                {
                    case "register":
                        userName = options[1].split(", ")[0];
                        pass = options[1].split(", ")[1];

                        if(db.checkUser(userName) == 0)
                        {
                            db.insertUser(userName, pass);
                            salida.println("registerok-User Registred successfully");
                            System.out.println("User Registred: " + userName + ", " + pass);

                            server.loadListUsers(db);
                        }
                        else
                        {
                            salida.println("registerbad-The user already exists");
                            System.out.println("User already exists: " + userName + ", " + pass);
                        }
                        break;
                    case "disconnect":
                        db.modifyState(userName, false);
                        server.removeIp(socket.getInetAddress().getHostAddress());
                        server.removeClient(this);
                        System.out.println("disconnection: " + userName + " pass: " + pass);

                        db.modifyState(userName, false);

                        server.loadListUsers(db);
                        break;
                    case "connect":
                        userName = options[1].split(", ")[0];
                        pass = options[1].split(", ")[1];

                        user.setName(userName);
                        user.setPass(pass);
                        if(db.checkConnection(userName, pass))
                        {
                            db.modifyState(userName, true);

                            System.out.println("login: " + userName + " pass: " + pass);
                            salida.println("loginok-Loged successfully");

                            if(db.getNumberUsers() > 1)
                            {
                                server.loadListUsers(db);
                            }
                        }
                        else
                        {
                            System.out.println("Wrong login: " + userName + " pass: " + pass);
                            salida.println("loginbad-Loged wrongly");
                        }
                        break;
                    case "message":
                        server.message(this, options);
                        break;
                    case "getUsers":
                        if(db.getNumberUsers() > 1)
                        {
                            String usuarios = db.getUsers(userName);
                            salida.println("users-" + usuarios);
                        }
                        else
                        {
                            salida.println("nousers-");
                        }
                        break;
                }
            }
        }
        catch (SocketException sqe)
        {
            server.removeIp(socket.getInetAddress().getHostAddress());
            try
            {
                server.removeClient(this);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            db.modifyState(userName, false);
        }
        catch(IOException ioex)
        {
            db.modifyState(userName, false);
            ioex.printStackTrace();
        }
    }
}