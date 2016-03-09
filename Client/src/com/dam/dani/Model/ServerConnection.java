package com.dam.dani.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection
{
    private String host = "localhost";
    private int puerto = 5555;

    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;

    public ServerConnection() throws IOException
    {
        Model m = new Model();
        host = m.readProperties();
        socket = new Socket(host, puerto);
        salida = new PrintWriter(socket.getOutputStream(), true);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public BufferedReader getEntrada()
    {
        return entrada;
    }

    public PrintWriter getSalida()
    {
        return salida;
    }

    public Socket getSocket()
    {
        return socket;
    }
}
