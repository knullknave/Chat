package com.dam.dani.View;

import com.dam.dani.Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Window
{
    public static JFrame frame;
    public JTextField textField1;
    public JButton sendbtn;
    public JPanel panel;
    public JList userList;
    public JTextArea textArea1;
    public JButton ignoreButton;
    public JButton leaveIgnoringButton;

    public static JMenuBar menuBar;
    public static JMenu menu;
    public static JMenuItem menuItem6, menuItem7, menuItem8, menuItem5, menuItem4;

    public Window()
    {
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("Description for menu");
        menuBar.add(menu);

        menuItem4 = new JMenuItem("Register", KeyEvent.VK_T);
        menuItem4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem4.getAccessibleContext().setAccessibleDescription("Description for fifth item");

        menuItem5 = new JMenuItem("Configure", KeyEvent.VK_T);
        menuItem5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem5.getAccessibleContext().setAccessibleDescription("Description for fifth item");

        menuItem6 = new JMenuItem("Exit", KeyEvent.VK_T);
        menuItem6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem6.getAccessibleContext().setAccessibleDescription("Description for sixth item");

        menuItem7 = new JMenuItem("Connect", KeyEvent.VK_T);
        menuItem7.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem7.getAccessibleContext().setAccessibleDescription("Description for seventh item");

        menuItem8 = new JMenuItem("Disconnect", KeyEvent.VK_T);
        menuItem8.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem8.getAccessibleContext().setAccessibleDescription("Description for eight item");

        menu.add(menuItem7);
        menuItem7.setMnemonic(KeyEvent.VK_D);
        menu.addSeparator();

        menu.add(menuItem8);
        menuItem8.setMnemonic(KeyEvent.VK_D);

        menu.addSeparator();

        menu.add(menuItem4);
        menuItem4.setMnemonic(KeyEvent.VK_D);

        menu.addSeparator();

        menu.add(menuItem5);
        menuItem5.setMnemonic(KeyEvent.VK_D);

        menu.addSeparator();

        menu.add(menuItem6);
        menuItem6.setMnemonic(KeyEvent.VK_D);

        sendbtn.setEnabled(false);
        ignoreButton.setEnabled(false);
        leaveIgnoringButton.setEnabled(false);

        textArea1.setLineWrap(true);

        Controller c = new Controller(Window.this);
    }

    public static void main(String[] args)
    {
        frame = new JFrame("Window");
        frame.setContentPane(new Window().panel);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setJMenuBar(menuBar);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}