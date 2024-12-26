package org.example.GUI;

import org.example.Connection;
import org.example.Main;
import org.example.SessionSystem.Session;
import org.example.User.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
                    if (Main.current_user != null)
                    {
                        if (Session.PasswordRequestAccepted(Main.current_user.getUserId()))
                        {
                            Window.getContentPane().removeAll();
                            InitializeChangePasswordPanel();
                            Window.add(Change_Password_Panel);
                            Window.revalidate();
                            Window.repaint();
                        }
                        else
                        {
                            Window.getContentPane().removeAll();
                            InitializeUserMenu();
                            Window.revalidate();
                            Window.repaint();
                        }
                    }
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

    static public JPanel Content_Panel = new JPanel(new GridBagLayout());
    public static void InitializeUserMenu()
    {
        InitializeProfilePanel();
        InitializeCreateTeamPanel();
        InitializeFilePanel();
        InitializeSettingsPanel();
        InitializeChangeUserNamePanel();

        JPanel File_Content_Panel = new JPanel();
        File_Content_Panel.setBackground(Color.RED);

        Content_Panel.add(File_Panel, setConstraints(GridBagConstraints.BOTH,1,1,0,0));
        Content_Panel.add(File_Content_Panel, setConstraints(GridBagConstraints.BOTH,1,1,1,0));
        Content_Panel.add(Profile_Panel, setConstraints(GridBagConstraints.BOTH,1,1,2,0));

        Window.add(Content_Panel);
        Window.setVisible(true);
    }

    static public JPanel File_Panel = new JPanel();
    public static void InitializeFilePanel()
    {
        File_Panel.setBackground(Color.BLUE);

        File directory = new File("src/main/java/org/example/Files/User/" + Main.current_user.getUserName());

        File[] files = directory.listFiles();

        for(File file : files)
        {
            JButton file_button = new JButton(file.getName());
            File_Panel.add(file_button);
        }

        JButton Upload_File_Button = new JButton("Upload File");
        Upload_File_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileChooser = new JFileChooser();

                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION)
                {
                    File selectedFile = fileChooser.getSelectedFile();

                    File targetDirectory = new File("src/main/java/org/example/Files/User/" + Main.current_user.getUserName());

                    File targetFile = new File(targetDirectory, selectedFile.getName());

                    try
                    {
                        Path sourcePath = selectedFile.toPath();
                        Path targetPath = targetFile.toPath();
                        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                        File_Panel.removeAll();
                        InitializeFilePanel();
                        Window.revalidate();
                        Window.repaint();
                    }
                    catch (IOException eb)
                    {
                        eb.printStackTrace();
                    }
                }
            }
        });
        File_Panel.add(Upload_File_Button);
    }

    static public JPanel Team_Panel = new JPanel();
    public static void InitializeTeamPanel()
    {
        Team_Panel.setBackground(Color.BLUE);

        JLabel Team_Name = new JLabel(Main.current_team.getName());
        Team_Panel.add(Team_Name);

        JLabel Team_Leader = new JLabel("Team Leader: " + Main.current_team.getTeam_Leader().getUserName());
        Team_Panel.add(Team_Leader);

        for (User team_member : Main.current_team.getTeam_Members())
        {
            JLabel Team_Member = new JLabel("Team Member: " + team_member.getUserName());
            Team_Panel.add(Team_Member);
        }

        JButton Add_Team_Member_Button = new JButton("Add Team Mate");
        Add_Team_Member_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                InitializeInputFrame();
            }
        });
        Team_Panel.add(Add_Team_Member_Button);

        JButton Close_Team_Panel_Button = new JButton("Close Team Panel");
        Close_Team_Panel_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Content_Panel.remove(Team_Panel);
                Content_Panel.add(File_Panel, setConstraints(GridBagConstraints.BOTH,1,1,0,0));
                Window.revalidate();
                Window.repaint();
            }
        });
        Team_Panel.add(Close_Team_Panel_Button);
    }

    static public JPanel Profile_Panel = new JPanel();
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
                Content_Panel.add(Create_Team_Panel, setConstraints(GridBagConstraints.BOTH,1,1,2,0));
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
                Content_Panel.add(Select_Team_Panel, setConstraints(GridBagConstraints.BOTH,1,1,2,0));
                Window.revalidate();
                Window.repaint();
            }
        });
        Profile_Panel.add(Select_Team_Button);

        JButton Settings_Button = new JButton("Settings");
        Settings_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Content_Panel.remove(Profile_Panel);
                Content_Panel.add(Settings_Panel, setConstraints(GridBagConstraints.BOTH,1,1,2,0));
                Window.revalidate();
                Window.repaint();
            }
        });
        Profile_Panel.add(Settings_Button);

        JButton Log_Out_Button = new JButton("Log Out");
        Log_Out_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Session.LogOut();
            }
        });
        Profile_Panel.add(Log_Out_Button);
    }

    static public JPanel Create_Team_Panel = new JPanel();
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
                    Main.current_user.CreateTeam(Team_Name_Field.getText(), Team_Mate_Field.getText());
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
                Content_Panel.add(Profile_Panel, setConstraints(GridBagConstraints.BOTH,1,1,2,0));
                Window.revalidate();
                Window.repaint();
            }
        });
        Create_Team_Panel.add(Return_Profile_Button);

    }

    static public JPanel Select_Team_Panel = new JPanel();
    public static void InitializeSelectTeamPanel()
    {
        Select_Team_Panel.removeAll();
        ArrayList<JButton> Teams;
        try
        {
            Teams = Session.GetAllTeams(Main.current_user.getUserName());
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
                Content_Panel.add(Profile_Panel, setConstraints(GridBagConstraints.BOTH,1,1,2,0));
                Window.revalidate();
                Window.repaint();
            }
        });
        Select_Team_Panel.add(Return_Profile_Button);
    }

    static public JPanel Settings_Panel = new JPanel();
    public static void InitializeSettingsPanel()
    {
        Settings_Panel.setBackground(Color.GREEN);

        JButton Change_User_Name_Button = new JButton("Change User Name");
        Change_User_Name_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Content_Panel.remove(Settings_Panel);
                Content_Panel.add(Change_User_Name_Panel, setConstraints(GridBagConstraints.BOTH,1,1,2,0));
                Window.revalidate();
                Window.repaint();
            }
        });
        Settings_Panel.add(Change_User_Name_Button);

        JButton Change_Password_Button = new JButton("Change Password");
        Change_Password_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Main.current_user.SendChangePasswordRequest();
            }
        });
        Settings_Panel.add(Change_Password_Button);

        JButton Return_Profile_Button = new JButton("Return Profile");
        Return_Profile_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Content_Panel.remove(Settings_Panel);
                Content_Panel.add(Profile_Panel, setConstraints(GridBagConstraints.BOTH,1,1,2,0));
                Window.revalidate();
                Window.repaint();
            }
        });
        Settings_Panel.add(Return_Profile_Button);
    }

    static public JPanel Change_User_Name_Panel = new JPanel();
    public static void InitializeChangeUserNamePanel()
    {
        Change_User_Name_Panel.setBackground(Color.GREEN);

        JTextField Previous_User_Name_Field = new JTextField(15);
        Change_User_Name_Panel.add(Previous_User_Name_Field);

        JTextField New_User_Name_Field = new JTextField(15);
        Change_User_Name_Panel.add(New_User_Name_Field);

        JButton Submit_button = new JButton("Submit");
        Submit_button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!Objects.equals(Previous_User_Name_Field.getText(), "") && !Objects.equals(New_User_Name_Field.getText(), ""))
                {
                    Main.current_user.ChangeUserName(Previous_User_Name_Field.getText(), New_User_Name_Field.getText());
                }
            }
        });
        Change_User_Name_Panel.add(Submit_button);

        JButton Return_Profile_Button = new JButton("Return Profile");
        Return_Profile_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Content_Panel.remove(Change_User_Name_Panel);
                Content_Panel.add(Profile_Panel, setConstraints(GridBagConstraints.BOTH,1,1,2,0));
                Window.revalidate();
                Window.repaint();
            }
        });
        Change_User_Name_Panel.add(Return_Profile_Button);
    }

    static public JPanel Change_Password_Panel = new JPanel();
    public static void InitializeChangePasswordPanel()
    {
        Change_Password_Panel.setBackground(Color.GREEN);

        JTextField Previous_Password_Field = new JTextField(15);
        Change_Password_Panel.add(Previous_Password_Field);

        JTextField New_Password_Field = new JTextField(15);
        Change_Password_Panel.add(New_Password_Field);

        JButton Submit_button = new JButton("Submit");
        Submit_button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!Objects.equals(Previous_Password_Field.getText(), "") && !Objects.equals(New_Password_Field.getText(), ""))
                {
                    if (BCrypt.checkpw(Previous_Password_Field.getText(), Main.current_user.getPassword()))
                    {
                        java.sql.Connection connection = null;
                        try
                        {
                            connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

                            String query = "UPDATE user SET Password = ? WHERE Password = ?";

                            PreparedStatement stmt = connection.prepareStatement(query);

                            stmt.setString(1, Session.EncryptPassword(New_Password_Field.getText()));
                            stmt.setString(2, Main.current_user.getPassword());

                            stmt.execute();

                            query = "DELETE FROM password_request WHERE Requested_User_Id = ?";
                            stmt = connection.prepareStatement(query);

                            stmt.setString(1, Main.current_user.getUserId());

                            stmt.execute();

                            stmt.close();
                            connection.close();

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
                }
            }
        });
        Change_Password_Panel.add(Submit_button);
    }

    static public  JFrame InputFrame;
    public static void InitializeInputFrame()
    {
        InputFrame = new JFrame();
        InputFrame.setSize(new Dimension(300, 100));
        InputFrame.setLayout(new FlowLayout());

        JTextField input_field = new JTextField(15);
        JButton validate_button = new JButton("Create");

        validate_button.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!input_field.getText().isEmpty())
                {
                    Main.current_user.AddTeamMate(Main.current_team.getId(), input_field.getText());
                    InputFrame.dispose();
                    Window.revalidate();
                    Window.repaint();
                }
                else
                {
                    System.out.println("A");
                }
            }
        });
        InputFrame.add(input_field);
        InputFrame.add(validate_button);
        InputFrame.setLocationRelativeTo(GUI_Elements.Window);
        InputFrame.setVisible(true);
    }

}
