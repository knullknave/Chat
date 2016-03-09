package com.dam.dani.View;

import com.dam.dani.Model.Model;

import javax.swing.*;
import java.awt.event.*;

public class Configuration extends JDialog
{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private Model m;

    public Configuration()
    {
        setContentPane(contentPane);
        pack();
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);

        m = new Model();

        textField1.setText(m.readProperties());

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
            public void windowClosing(WindowEvent e) {
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
        if(textField1.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please, fill each field", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            m.writeProperties(textField1.getText());
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
