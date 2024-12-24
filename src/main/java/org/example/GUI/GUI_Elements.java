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
import java.util.ArrayList;
import java.util.Objects;

public class GUI_Elements
{

    public static GridBagConstraints setConstraints(int fill,int gridwidth, int gridheight, int gridx, int gridy)
    {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = fill;
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

        LogInPanel.add(User_Name_Panel, setConstraints(GridBagConstraints.NONE,1, 1, 0 ,0));
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

        LogInPanel.add(Password_Panel,  setConstraints(GridBagConstraints.NONE,1,1,0,1));
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
                    Window.getContentPane().removeAll();
                    InitializeUserMenu();
                    Window.revalidate();
                    Window.repaint();
                }
                catch (SQLException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
        });
        LogInPanel.add(LogIn_Button, setConstraints(GridBagConstraints.NONE,1,1,0,2));
        //--

        //--
        JButton Register_Button = new JButton("Register");
        Register_Button.setPreferredSize(new Dimension(300, 30));
        Register_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Window.getContentPane().removeAll();
                InitializeRegisterMenu();
                Window.revalidate();
                Window.repaint();
            }
        });
        LogInPanel.add(Register_Button, setConstraints(GridBagConstraints.NONE,1,1,0,3));
        //--

        Window.add(LogInPanel);
        Window.setVisible(true);
    }

    public static void InitializeRegisterMenu()
    {
        //--
        JPanel Register_Panel = new JPanel(new GridBagLayout());
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

        Register_Panel.add(User_Name_Panel, setConstraints(GridBagConstraints.NONE,1, 1, 0 ,0));
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

        Register_Panel.add(First_Name_Panel, setConstraints(GridBagConstraints.NONE,1, 1, 0 ,1));
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

        Register_Panel.add(Last_Name_Panel, setConstraints(GridBagConstraints.NONE,1, 1, 0 ,2));
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

        Register_Panel.add(Password_Panel,  setConstraints(GridBagConstraints.NONE,1,1,0,3));
        //--

        //--
        JButton Register_Button = new JButton("Register");
        Register_Button.setPreferredSize(new Dimension(300, 30));
        Register_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    if (!Objects.equals(User_Name_Field.getText(), "") && !Objects.equals(First_Name_Field.getText(), "") &&
                        !Objects.equals(Last_Name_Field.getText(), "") && !Objects.equals(Password_Field.getText(), ""))
                    {
                        Session.Register(User_Name_Field.getText(), First_Name_Field.getText(),
                                         Last_Name_Field.getText(), Password_Field.getText());
                    }
                    else
                    {
                        System.out.println("Herhangi biri boş kardeşim");
                    }

                }
                catch (SQLException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
        });
        Register_Panel.add(Register_Button, setConstraints(GridBagConstraints.NONE,1,1,0,4));
        //--

        //--
        JButton LogIn_Button = new JButton("Log In");
        LogIn_Button.setPreferredSize(new Dimension(300, 30));
        LogIn_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Window.getContentPane().removeAll();
                InitializeLogInMenu();
                Window.revalidate();
                Window.repaint();
            }
        });
        Register_Panel.add(LogIn_Button, setConstraints(GridBagConstraints.NONE,1,1,0,5));
        //--

        Window.add(Register_Panel);
        Window.setVisible(true);
    }

    static JPanel Content_Panel = new JPanel(new GridBagLayout());
    public static void InitializeUserMenu()
    {
        InitializeProfilePanel();
        InitializeCreateTeamPanel();

        JPanel Team_Panel = new JPanel();
        Team_Panel.setBackground(Color.BLUE);

        JPanel File_Panel = new JPanel();
        File_Panel.setBackground(Color.RED);

        Content_Panel.add(Team_Panel, setConstraints(GridBagConstraints.BOTH,1,1,0,0));
        Content_Panel.add(File_Panel, setConstraints(GridBagConstraints.BOTH,1,1,1,0));
        Content_Panel.add(Profile_Panel, setConstraints(GridBagConstraints.BOTH,1,1,2,0));

        Window.add(Content_Panel);
        Window.setVisible(true);
    }

    static JPanel Profile_Panel = new JPanel();
    public static void InitializeProfilePanel()
    {
        Profile_Panel.setBackground(Color.GREEN);

        JLabel User_Name = new JLabel(Main.current_user.getUserName());
        User_Name.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        Profile_Panel.add(User_Name);

        JButton Create_Team_Button = new JButton("Create Team");
        Create_Team_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Content_Panel.remove(Profile_Panel);
                Content_Panel.add(Create_Team_Panel);
                Window.revalidate();
                Window.repaint();
            }
        });
        Profile_Panel.add(Create_Team_Button);

        JButton Select_Team_Button = new JButton("Select Team");
        Select_Team_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Content_Panel.remove(Profile_Panel);
                InitializeSelectTeamPanel();
                Content_Panel.add(Select_Team_Panel);
                Window.revalidate();
                Window.repaint();
            }
        });
        Profile_Panel.add(Select_Team_Button);

        JButton Settings_Button = new JButton("Settings");
        Profile_Panel.add(Settings_Button);
    }

    static JPanel Create_Team_Panel = new JPanel();
    public static void InitializeCreateTeamPanel()
    {
        Create_Team_Panel.setBackground(Color.GREEN);

        JTextField Team_Name_Field = new JTextField(15);
        Team_Name_Field.setPreferredSize(new Dimension(0, 30));
        Create_Team_Panel.add(Team_Name_Field);

        JTextField Team_Mate_Field = new JTextField(15);
        Team_Mate_Field.setPreferredSize(new Dimension(0, 30));
        Create_Team_Panel.add(Team_Mate_Field);

        JButton Create_Button = new JButton("Create");
        Create_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!Objects.equals(Team_Name_Field.getText(), "") && !Objects.equals(Team_Mate_Field.getText(), ""))
                {
                    try
                    {
                        Main.current_user.CreateTeam(Team_Name_Field.getText(), Team_Mate_Field.getText());
                    }
                    catch (SQLException ex)
                    {
                        throw new RuntimeException(ex);
                    }
                }
                else
                {
                    System.out.println("Boş");
                }

            }
        });
        Create_Team_Panel.add(Create_Button);

        JButton Return_Profile_Button = new JButton("Return Profile");
        Return_Profile_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Content_Panel.remove(Create_Team_Panel);
                Content_Panel.add(Profile_Panel);
                Window.revalidate();
                Window.repaint();
            }
        });
        Create_Team_Panel.add(Return_Profile_Button);

    }

    static JPanel Select_Team_Panel = new JPanel();
    public static void InitializeSelectTeamPanel()
    {
        Select_Team_Panel.removeAll();
        ArrayList<JButton> Teams;
        try
        {
            Teams = Main.current_user.GetAllTeams();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        Select_Team_Panel.setBackground(Color.GREEN);
        for (JButton team : Teams)
        {
            Select_Team_Panel.add(team);
        }

        JButton Return_Profile_Button = new JButton("Return Profile");
        Return_Profile_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Content_Panel.remove(Select_Team_Panel);
                Content_Panel.add(Profile_Panel);
                Window.revalidate();
                Window.repaint();
            }
        });
        Select_Team_Panel.add(Return_Profile_Button);
    }

}
