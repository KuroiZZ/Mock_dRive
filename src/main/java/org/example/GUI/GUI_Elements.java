package org.example.GUI;

import com.mysql.cj.log.Log;
import org.example.Main;
import org.example.SessionSystem.Session;
import org.example.User.User;

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
        Window.getContentPane().setBackground(Color.RED);
    }

    public static void InitializeLogInMenu()
    {
        //--
        JPanel LogInPanel = new JPanel(new GridBagLayout());
        LogInPanel.setBackground(Color.BLUE);
        //--

        //--
        JPanel User_Name_Panel = new JPanel(new FlowLayout());
        User_Name_Panel.setOpaque(false);

        JLabel User_Name = new JLabel("Username:");
        User_Name.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        User_Name_Panel.add(User_Name);

        JTextField User_Name_Field = new JTextField(15);
        User_Name_Field.setPreferredSize(new Dimension(0, 30));
        User_Name_Panel.add(User_Name_Field);

        LogInPanel.add(User_Name_Panel, setConstraints(1, 1, 0 ,0));
        //--

        //--
        JPanel Password_Panel = new JPanel(new FlowLayout());
        Password_Panel.setOpaque(false);

        JLabel Password = new JLabel("Password:");
        Password.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        Password_Panel.add(Password);

        JPasswordField Password_Field = new JPasswordField(15);
        Password_Field.setPreferredSize(new Dimension(0, 30));
        Password_Panel.add(Password_Field);

        LogInPanel.add(Password_Panel,  setConstraints(1,1,0,1));
        //--

        //--
        JButton LogIn_Button = new JButton("Log In");
        LogIn_Button.setPreferredSize(new Dimension(300, 30));
        LogIn_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    Main.current_user =  Session.LogIn(User_Name_Field.getText(), Password_Field.getText());
                }
                catch (SQLException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
        });
        LogInPanel.add(LogIn_Button, setConstraints(1,1,0,2));
        //--

        Window.add(LogInPanel);
        Window.setVisible(true);
    }

    public static void InitializeRegisterMenu()
    {
        //--
        JPanel Register_Panel = new JPanel(new GridBagLayout());
        Register_Panel.setBackground(Color.BLUE);
        //--

        //--
        JPanel User_Name_Panel = new JPanel(new FlowLayout());
        User_Name_Panel.setOpaque(false);

        JLabel User_Name = new JLabel("Username:");
        User_Name.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        User_Name_Panel.add(User_Name);

        JTextField User_Name_Field = new JTextField(15);
        User_Name_Field.setPreferredSize(new Dimension(0, 30));
        User_Name_Panel.add(User_Name_Field);

        Register_Panel.add(User_Name_Panel, setConstraints(1, 1, 0 ,0));
        //--

        //--
        JPanel First_Name_Panel = new JPanel(new FlowLayout());
        First_Name_Panel.setOpaque(false);

        JLabel First_Name = new JLabel("First Name:");
        First_Name.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        First_Name_Panel.add(First_Name);

        JTextField First_Name_Field = new JTextField(15);
        First_Name_Field.setPreferredSize(new Dimension(0, 30));
        First_Name_Panel.add(First_Name_Field);

        Register_Panel.add(First_Name_Panel, setConstraints(1, 1, 0 ,1));
        //--

        //--
        JPanel Last_Name_Panel = new JPanel(new FlowLayout());
        Last_Name_Panel.setOpaque(false);

        JLabel Last_Name = new JLabel("Last Name:");
        Last_Name.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        Last_Name_Panel.add(Last_Name);

        JTextField Last_Name_Field = new JTextField(15);
        Last_Name_Field.setPreferredSize(new Dimension(0, 30));
        Last_Name_Panel.add(Last_Name_Field);

        Register_Panel.add(Last_Name_Panel, setConstraints(1, 1, 0 ,2));
        //--


        //--
        JPanel Password_Panel = new JPanel(new FlowLayout());
        Password_Panel.setOpaque(false);

        JLabel Password = new JLabel("Password:");
        Password.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        Password_Panel.add(Password);

        JPasswordField Password_Field = new JPasswordField(15);
        Password_Field.setPreferredSize(new Dimension(0, 30));
        Password_Panel.add(Password_Field);

        Register_Panel.add(Password_Panel,  setConstraints(1,1,0,3));
        //--

        //--
        JButton LogIn_Button = new JButton("Register");
        LogIn_Button.setPreferredSize(new Dimension(300, 30));
        LogIn_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    Session.Register(User_Name_Field.getText(), First_Name_Field.getText(), Last_Name_Field.getText(),Password_Field.getText());
                }
                catch (SQLException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
        });
        Register_Panel.add(LogIn_Button, setConstraints(1,1,0,4));
        //--

        Window.add(Register_Panel);
        Window.setVisible(true);
    }

}
