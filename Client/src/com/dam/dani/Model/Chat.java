package com.dam.dani.Model;

import com.dam.dani.Controller.Controller;

import javax.swing.*;
import java.io.IOException;

public class Chat implements Runnable
{
    private Controller c;

    public Chat(Controller c)
    {
        this.c = c;
    }

    @Override
    public void run()
    {
        while (true)
        {
            while (c.connected)
            {
                if(c.serverConnection != null)
                {
                    if(c.serverConnection.getSocket().isClosed())
                    {
                        c.connected = false;
                        break;
                    }

                    try
                    {
                        String message = c.serverConnection.getEntrada().readLine();

                        if(message == null)
                            continue;
                        if(message.startsWith("registerok"))
                        {
                            JOptionPane.showMessageDialog(null, "User registered correctly", "Correct", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else if(message.startsWith("registerbad"))
                        {
                            JOptionPane.showMessageDialog(null, "User registered wrongly", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else if(message.startsWith("loginok"))
                        {
                            JOptionPane.showMessageDialog(null, "Loged correctly", "Correct", JOptionPane.INFORMATION_MESSAGE);
                            c.callListar();
                            c.w.menuItem7.setEnabled(false);
                        }
                        else if(message.startsWith("loginbad"))
                        {
                            JOptionPane.showMessageDialog(null, "The user name or password are incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                            c.user = "";
                        }
                        else if(message.startsWith("message"))
                        {
                            String name = message.split("-")[1].split(", ")[0];
                            String message1 = message.split("-")[1].split(", ")[1];
                            if (!c.m.readFile().contains(name))
                                c.w.textArea1.setText(c.w.textArea1.getText() + name + ": " + message1 + "\n");
                        }
                        else if(message.startsWith("users"))
                        {
                            String[] users = message.split("-")[1].split(", ");
                            c.listUsers(users);
                        }
                    }
                    catch(IOException ioex)
                    {
                        ioex.printStackTrace();
                        c.serverConnection = null;
                        JOptionPane.showMessageDialog(null, "The connection has been lost", "Error", JOptionPane.ERROR_MESSAGE);
                        c.connected = false;
                    }
                }
            }
            c.connected = false;
        }
    }
}