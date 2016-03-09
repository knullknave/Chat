package com.dam.dani.Controller;

import com.dam.dani.Model.Chat;
import com.dam.dani.Model.Model;
import com.dam.dani.Model.ServerConnection;
import com.dam.dani.View.Configuration;
import com.dam.dani.View.Connect;
import com.dam.dani.View.Window;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ActionListener, ListSelectionListener
{
    public Window w;
    public ServerConnection serverConnection;
    public boolean connected;
    public String userSelected;
    public DefaultListModel modelo;
    public Model m;
    public String user;
    public List<String> ignorados;

    public Controller(Window w)
    {
        this.w = w;
        m = new Model();
        ignorados = new ArrayList<>();
        ignorados = m.readFile();
        modelo = new DefaultListModel();
        this.w.userList.setModel(modelo);

        this.w.menuItem4.addActionListener(this);
        this.w.menuItem5.addActionListener(this);
        this.w.menuItem6.addActionListener(this);
        this.w.menuItem7.addActionListener(this);
        this.w.menuItem8.addActionListener(this);

        w.userList.addListSelectionListener(this);

        this.w.sendbtn.addActionListener(this);
        w.ignoreButton.addActionListener(this);
        w.leaveIgnoringButton.addActionListener(this);

        if(!new File("config.props").exists())
        {
            m.writeProperties("localhost");
        }

        tryConnection();

        Chat c = new Chat(this);
        Thread hilo = new Thread(c);
        hilo.start();

        w.frame.addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                if (serverConnection == null)
                {
                    w.frame.dispose();
                    System.exit(0);
                }
                else
                {
                    serverConnection.getSalida().println("disconnect-");
                    connected = false;
                    w.frame.dispose();
                    System.exit(0);
                }
            }
        });
    }

    public void tryConnection()
    {
        try
        {
            serverConnection = new ServerConnection();
            if (serverConnection != null)
            {
                connected = true;
                JOptionPane.showMessageDialog(null, "You have been connected to the server successfully", "Correct", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "You could not connect to the server", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void callListar()
    {
        serverConnection.getSalida().println("getUsers-");
    }

    public void listUsers(String[] users)
    {
        modelo.removeAllElements();
        for(int i=0; i<users.length; i++)
        {
            if(i%2 == 0)
            {
                String conexion = "";
                if(users[i+1].equals("0"))
                    conexion = "Disconnected";
                else
                    conexion = "Connected";
                modelo.addElement(users[i] + ", " + conexion);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        if(source.getClass() == JButton.class)
        {
            String actionCommand = ((JButton) e.getSource()).getActionCommand();
            switch (actionCommand)
            {
                case "Send":
                    if (serverConnection != null && connected == true)
                    {
                        String mensaje = "message-" + w.textField1.getText() + ", " + userSelected;
                        w.textArea1.setText(w.textArea1.getText() + user + ": " + w.textField1.getText() + "\n");
                        serverConnection.getSalida().println(mensaje);

                        String[] lineas = w.textArea1.getText().split("\n");
                        m.writeFile(userSelected, lineas);
                    }
                    w.textField1.setText("");
                    break;
                case "Ignore":

                        if (!ignorados.contains(userSelected)) {
                            m.writeFile(userSelected, true);
                            ignorados.add(userSelected);
                        }

                    break;
                case "Leave Ignoring":

                        if (ignorados.contains(userSelected)) {
                            m.removeLine(userSelected);
                            ignorados.remove(userSelected);
                        }

                    break;
            }
        }
        else
        {
            String actionCommand2 = ((JMenuItem) e.getSource()).getActionCommand();

            switch (actionCommand2)
            {
                case "Exit":
                    System.exit(0);
                    break;
                case "Connect":
                    w.userList.clearSelection();
                    w.sendbtn.setEnabled(false);
                    if(connected == true)
                    {
                        Connect c = new Connect(this, 0);
                        c.mostrar();
                    }
                    else
                    {
                        tryConnection();
                        if(connected == true)
                        {
                            Connect c = new Connect(this, 0);
                            c.mostrar();
                        }
                    }

                    break;
                case "Disconnect":
                    if (serverConnection == null)
                    {
                        w.frame.dispose();
                        System.exit(0);
                    }
                    else
                    {
                        serverConnection.getSalida().println("disconnect-");
                        connected = false;
                        w.frame.dispose();
                        System.exit(0);
                    }
                    break;
                case "Configure":
                    w.userList.clearSelection();
                    w.sendbtn.setEnabled(false);
                    w.ignoreButton.setEnabled(false);
                    w.leaveIgnoringButton.setEnabled(false);
                    Configuration conf = new Configuration();
                    conf.mostrar();
                    break;
                case "Register":
                    w.userList.clearSelection();
                    w.sendbtn.setEnabled(false);
                    w.ignoreButton.setEnabled(false);
                    w.leaveIgnoringButton.setEnabled(false);
                    Connect c2 = new Connect(this, 1);
                    c2.mostrar();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        if(!w.userList.isSelectionEmpty())
        {
            //System.out.print(w.textArea1.getText());
            String[] lineas = w.textArea1.getText().split("\n");
            m.writeFile(userSelected, lineas);
            w.textArea1.setText("");
            userSelected = String.valueOf(w.userList.getSelectedValue()).split(", ")[0];
            String connected = String.valueOf(w.userList.getSelectedValue()).split(", ")[1];
            if(connected.equals("Connected"))
            {
                w.sendbtn.setEnabled(true);
            }
            else
            {
                w.sendbtn.setEnabled(false);
            }

            w.ignoreButton.setEnabled(true);
            w.leaveIgnoringButton.setEnabled(true);

            if(new File(userSelected + ".txt").exists())
            {
                ArrayList<String> listado = m.readFile(userSelected);
                for(int i =0 ; i<listado.size(); i++)
                {
                    w.textArea1.setText(w.textArea1.getText() + listado.get(i) + "\n");
                }
            }
        }
    }
}