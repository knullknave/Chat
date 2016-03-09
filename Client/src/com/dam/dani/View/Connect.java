package com.dam.dani.View;

import com.dam.dani.Controller.Controller;
import com.dam.dani.Model.ServerConnection;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.event.*;

public class Connect extends JDialog
{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private Controller c;
    private int sw;

    public Connect(Controller c, int sw)
    {
        this.c = c;
        this.sw = sw;
        initialize();


    }

    public void initialize()
    {
        setContentPane(contentPane);
        pack();
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);

        buttonOK.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK()
    {
        if(textField1.getText().equals("") || textField2.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please, fill each field", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            if(sw == 0)
            {
                c.serverConnection.getSalida().println("connect-" + textField1.getText() + ", " + textField2.getText());
                c.user = textField1.getText();
            }
            else
            {
                c.serverConnection.getSalida().println("register-" + textField1.getText() + ", " + textField2.getText());
            }
            dispose();
        }

    }

    private void onCancel()
    {
        dispose();
    }

    public void mostrar()
    {
        setVisible(true);
    }
}