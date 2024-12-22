package org.example.GUI;

import org.example.SessionSystem.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class GUI_Elements
{

    public static GridBagConstraints setConstraints(int gridwidth, int gridheight, int gridx, int gridy)
    {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.gridx = gridx;
        constraints.gridy = gridy;

        return constraints;
    }

    public static JFrame Window = new JFrame();
    public static void InitializeWindowProperties()
    {
        Window.setSize(600, 400);
        Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Window.getContentPane().setBackground(Color.BLACK);
    }

    public static void InitializeLogInMenu()
    {
        JPanel LogInPanel = new JPanel(new GridBagLayout());
        LogInPanel.setPreferredSize(new Dimension(960, 540));
        LogInPanel.setBackground(Color.BLACK);


        JTextField User_Name_Field = new JTextField(15);
        User_Name_Field.setPreferredSize(new Dimension(0, 30));
        LogInPanel.add(User_Name_Field, setConstraints(1, 1, 0 ,0));


        JTextField Password_Field = new JTextField(15);
        Password_Field.setPreferredSize(new Dimension(0, 30));
        LogInPanel.add(Password_Field, setConstraints(1,1,0,1));


        JButton LogIn_Button = new JButton("Log In");
        LogIn_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    Session.LogIn(User_Name_Field.getText(), Password_Field.getText());
                }
                catch (SQLException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
        });
        LogInPanel.add(LogIn_Button, setConstraints(1,1,0,2));


        Window.add(LogInPanel);
        Window.setVisible(true);
    }

}
