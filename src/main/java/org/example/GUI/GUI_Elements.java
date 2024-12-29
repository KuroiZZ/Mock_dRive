package org.example.GUI;

import org.example.Connection;
import org.example.Process.Process;
import org.example.SessionSystem.Loggers;
import org.example.Main;
import org.example.SessionSystem.Session;
import org.example.User.Role;
import org.example.User.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        Window.setLayout(new GridLayout(1,2));
        Window.setSize(1200, 700);
        Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Window.getContentPane().setBackground(Color.RED);

        Window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        Window.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                Process.process_Watcher.destroy();

                Process.scheduler_Anomaly_Password_Request.shutdown();
                Process.process_Anomaly_Password_Request.destroy();

                Process.scheduler_Anomaly_Session.shutdown();
                Process.process_Anomaly_Session.destroy();

                Process.scheduler_Backup_Request.shutdown();
                Process.process_Backup_Anomaly.destroy();

                Process.process_Backup.destroy();
                Process.scheduler_Backup.shutdown();



                Window.dispose();
            }
        });
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
                        if(Main.current_user.getRole() == Role.CUSTOMER)
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
                        else if (Main.current_user.getRole() == Role.ADMIN)
                        {
                            Window.getContentPane().removeAll();
                            InitializeAdminMenu();
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

        JLabel User_Name = new JLabel("Username:  ");
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

        JLabel Last_Name = new JLabel("Last Name: ");
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

        JLabel Password = new JLabel("Password:  ");
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


        Content_Panel.add(File_Panel, setConstraints(GridBagConstraints.BOTH,1,1,0,0));
        Content_Panel.add(Profile_Panel, setConstraints(GridBagConstraints.BOTH,1,1,1,0));

        Window.add(Content_Panel);
        Window.setVisible(true);
    }

    public static void InitializeAdminMenu()
    {
        Content_Panel = new JPanel(new GridBagLayout());
        Content_Panel.setBackground(Color.PINK);

        JLabel User_Name_Label = new JLabel(Main.current_user.getUserName());
        User_Name_Label.setFont(new Font(Font.SERIF, Font.BOLD, 35));
        Content_Panel.add(User_Name_Label, setConstraints(GridBagConstraints.CENTER, 3, 1, 0, 0));

        JButton Manage_Users_Button = new JButton("Manage Users");
        Manage_Users_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                InitializeUsersPanel();
                Content_Panel.removeAll();
                Content_Panel.add(Users_Panel);
                Window.revalidate();
                Window.repaint();
            }
        });
        Content_Panel.add(Manage_Users_Button, setConstraints(GridBagConstraints.CENTER, 1,1,0,1));

        JButton Manage_Password_Button = new JButton("Manage Passwords");
        Manage_Password_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                InitializeRequestsPanel();
                Content_Panel.removeAll();
                Content_Panel.add(Requests_Panel);
                Window.revalidate();
                Window.repaint();
            }
        });
        Content_Panel.add(Manage_Password_Button, setConstraints(GridBagConstraints.CENTER, 1,1,1,1));

        JButton Show_Log_Files = new JButton("Show Log Files");
        Show_Log_Files.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                InitializeLogFilesPanel();
                Content_Panel.removeAll();
                Content_Panel.add(Log_Files_Panel);
                Window.revalidate();
                Window.repaint();
            }
        });
        Content_Panel.add(Show_Log_Files, setConstraints(GridBagConstraints.CENTER, 1,1,2,1));

        JButton Log_Out_Button = new JButton("Log Out");
        Log_Out_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Session.LogOut();
            }
        });
        Content_Panel.add(Log_Out_Button, setConstraints(GridBagConstraints.CENTER, 3,1,0,2));

        Window.add(Content_Panel);
        Window.setVisible(true);
    }

    static public JPanel File_Panel = new JPanel(new GridBagLayout());
    public static void InitializeFilePanel()
    {
        File_Panel.setBackground(Color.BLUE);

        File directory = new File("src/main/java/org/example/Files/User/" + Main.current_user.getUserName());

        File[] files = directory.listFiles();
        int index = 0;

        for(File file : files)
        {
            File_Button file_button = new File_Button(file);
            file_button.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if (Desktop.isDesktopSupported())
                    {
                        try
                        {
                            Desktop.getDesktop().open(file);
                        }
                        catch (IOException ex)
                        {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
            File_Panel.add(file_button, setConstraints(GridBagConstraints.NONE, 1, 1, 1, index));
            index++;
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
        Upload_File_Button.setPreferredSize(new Dimension(250, 50));
        File_Panel.add(Upload_File_Button, setConstraints(GridBagConstraints.NONE, 1, 1, 1, index));
    }

    static public JPanel Team_Panel = new JPanel(new GridBagLayout());
    public static void InitializeTeamPanel()
    {
        int index = 0;
        Team_Panel.setBackground(Color.BLUE);

        JLabel Team_Name = new JLabel(Main.current_team.getName());
        Team_Name.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        Team_Panel.add(Team_Name, setConstraints(GridBagConstraints.NONE, 1, 1 ,0 ,index));
        index++;

        JLabel Team_Leader = new JLabel("Team Leader: " + Main.current_team.getTeam_Leader().getUserName());
        Team_Leader.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        Team_Panel.add(Team_Leader, setConstraints(GridBagConstraints.NONE, 1, 1 ,0 ,index));
        index++;

        for (User team_member : Main.current_team.getTeam_Members())
        {
            JLabel Team_Member = new JLabel("Team Member: " + team_member.getUserName());
            Team_Member.setFont(new Font(Font.SERIF, Font.BOLD, 25));
            Team_Panel.add(Team_Member, setConstraints(GridBagConstraints.NONE, 1, 1 ,0 ,index));
            index++;
        }

        JButton Files_Button = new JButton("Files");
        Files_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                InitializeTeamFileFrame();
            }
        });
        Files_Button.setPreferredSize(new Dimension(250, 50));
        Team_Panel.add(Files_Button, setConstraints(GridBagConstraints.NONE, 1,1,0,index));
        index++;

        JButton Add_Team_Member_Button = new JButton("Add Team Mate");
        Add_Team_Member_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                InitializeInputFrame();
            }
        });
        Add_Team_Member_Button.setPreferredSize(new Dimension(250, 50));
        Team_Panel.add(Add_Team_Member_Button, setConstraints(GridBagConstraints.NONE, 1,1,0,index));
        index++;

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
        Close_Team_Panel_Button.setPreferredSize(new Dimension(250, 50));
        Team_Panel.add(Close_Team_Panel_Button, setConstraints(GridBagConstraints.NONE, 1,1,0,index));
    }

    static public JPanel Profile_Panel = new JPanel(new GridBagLayout());
    public static void InitializeProfilePanel()
    {
        Profile_Panel.setBackground(Color.GREEN);

        JLabel User_Name = new JLabel(Main.current_user.getUserName());
        User_Name.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        Profile_Panel.add(User_Name, setConstraints(GridBagConstraints.NONE, 1,1,0,0));

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
        Create_Team_Button.setPreferredSize(new Dimension(250,50));
        Profile_Panel.add(Create_Team_Button, setConstraints(GridBagConstraints.NONE, 1,1,0,1));

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
        Select_Team_Button.setPreferredSize(new Dimension(250,50));
        Profile_Panel.add(Select_Team_Button, setConstraints(GridBagConstraints.NONE, 1,1,0,2));

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
        Settings_Button.setPreferredSize(new Dimension(250,50));
        Profile_Panel.add(Settings_Button, setConstraints(GridBagConstraints.NONE, 1,1,0,3));

        JButton Log_Out_Button = new JButton("Log Out");
        Log_Out_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Session.LogOut();
            }
        });
        Log_Out_Button.setPreferredSize(new Dimension(250,50));
        Profile_Panel.add(Log_Out_Button, setConstraints(GridBagConstraints.NONE, 1,1,0,4));
    }

    static public JPanel Create_Team_Panel = new JPanel(new GridBagLayout());
    public static void InitializeCreateTeamPanel()
    {
        Create_Team_Panel.setBackground(Color.GREEN);

        JPanel Team_Name_Panel= new JPanel(new FlowLayout());
        Team_Name_Panel.setOpaque(false);

        JLabel Team_Name_Label = new JLabel("Team Name: ");
        Team_Name_Label.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        Team_Name_Panel.add(Team_Name_Label);

        JTextField Team_Name_Field = new JTextField(15);
        Team_Name_Field.setPreferredSize(new Dimension(0, 30));
        Team_Name_Panel.add(Team_Name_Field);

        Create_Team_Panel.add(Team_Name_Panel, setConstraints(GridBagConstraints.NONE, 1, 1, 0 ,0));


        JPanel Team_Mate_Panel = new JPanel(new FlowLayout());
        Team_Mate_Panel.setOpaque(false);

        JLabel Team_Mate_Label = new JLabel("Team Mate: ");
        Team_Mate_Label.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        Team_Mate_Panel.add(Team_Mate_Label);

        JTextField Team_Mate_Field = new JTextField(15);
        Team_Mate_Field.setPreferredSize(new Dimension(0, 30));
        Team_Mate_Panel.add(Team_Mate_Field);

        Create_Team_Panel.add(Team_Mate_Panel, setConstraints(GridBagConstraints.NONE, 1, 1, 0, 1));


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
        Create_Button.setPreferredSize(new Dimension(250, 50));

        Create_Team_Panel.add(Create_Button, setConstraints(GridBagConstraints.NONE, 1, 1, 0, 2));

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
        Return_Profile_Button.setPreferredSize(new Dimension(250, 50));

        Create_Team_Panel.add(Return_Profile_Button, setConstraints(GridBagConstraints.NONE, 1, 1, 0, 3));

    }

    static public JPanel Select_Team_Panel = new JPanel(new GridBagLayout());
    public static void InitializeSelectTeamPanel()
    {
        int index = 0;
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
            Select_Team_Panel.add(team, setConstraints(GridBagConstraints.NONE, 1,1,0,index));
            index++;
        }

        JButton Return_Profile_Button = new JButton("Return Profile");
        Return_Profile_Button.setPreferredSize(new Dimension(250, 50));
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
        Select_Team_Panel.add(Return_Profile_Button, setConstraints(GridBagConstraints.NONE, 1,1,0,index));
    }

    static public JPanel Settings_Panel = new JPanel(new GridBagLayout());
    public static void InitializeSettingsPanel()
    {
        Settings_Panel.setBackground(Color.GREEN);

        JButton Change_User_Name_Button = new JButton("Change User Name");
        Change_User_Name_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Content_Panel.remove(Settings_Panel);
                Content_Panel.add(Change_User_Name_Panel, setConstraints(GridBagConstraints.BOTH,1,1,2,0));
                Window.revalidate();
                Window.repaint();
            }
        });
        Change_User_Name_Button.setPreferredSize(new Dimension(250, 50));
        Settings_Panel.add(Change_User_Name_Button, setConstraints(GridBagConstraints.NONE, 1, 1, 0 ,0));

        JButton Change_Password_Button = new JButton("Change Password");
        Change_Password_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Main.current_user.SendChangePasswordRequest();
            }
        });
        Change_Password_Button.setPreferredSize(new Dimension(250, 50));
        Settings_Panel.add(Change_Password_Button, setConstraints(GridBagConstraints.NONE, 1, 1, 0 ,1));

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
        Return_Profile_Button.setPreferredSize(new Dimension(250, 50));
        Settings_Panel.add(Return_Profile_Button, setConstraints(GridBagConstraints.NONE, 1, 1, 0 ,2));
    }

    static public JPanel Change_User_Name_Panel = new JPanel(new GridBagLayout());
    public static void InitializeChangeUserNamePanel()
    {
        Change_User_Name_Panel.setBackground(Color.GREEN);

        JPanel Previous_Panel = new JPanel(new FlowLayout());
        Previous_Panel.setOpaque(false);

        JLabel Previous_Name = new JLabel("Previous: ");
        Previous_Name.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        Previous_Panel.add(Previous_Name);

        JTextField Previous_User_Name_Field = new JTextField(15);
        Previous_User_Name_Field.setPreferredSize(new Dimension(0, 30));
        Previous_Panel.add(Previous_User_Name_Field);
        Change_User_Name_Panel.add(Previous_Panel, setConstraints(GridBagConstraints.NONE, 1, 1,0,0));

        JPanel New_Panel = new JPanel(new FlowLayout());
        New_Panel.setOpaque(false);

        JLabel New_Name = new JLabel("New:      ");
        New_Name.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        New_Panel.add(New_Name);

        JTextField New_User_Name_Field = new JTextField(15);
        New_User_Name_Field.setPreferredSize(new Dimension(0, 30));
        New_Panel.add(New_User_Name_Field);
        Change_User_Name_Panel.add(New_Panel, setConstraints(GridBagConstraints.NONE, 1, 1,0,1));



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
        Submit_button.setPreferredSize(new Dimension(250, 50));
        Change_User_Name_Panel.add(Submit_button, setConstraints(GridBagConstraints.NONE, 1, 1,0,2));

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
        Return_Profile_Button.setPreferredSize(new Dimension(250, 50));
        Change_User_Name_Panel.add(Return_Profile_Button, setConstraints(GridBagConstraints.NONE, 1, 1,0,3));
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

                            String logMessage = "User " +  Main.current_user.getUserName() + " changed password";
                            Loggers.password_request_logger.info(logMessage);

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

    static public JFrame SelectForShareFrame;
    public static void InitializeSelectForShareFrame(File file)
    {
        SelectForShareFrame = new JFrame();
        SelectForShareFrame.setSize(new Dimension(300, 900));
        SelectForShareFrame.setLayout(new FlowLayout());

        ArrayList<JButton> Teams;
        try
        {
            Teams = Session.GetAllTeamsFile(Main.current_user.getUserName(), file);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        for (JButton team : Teams)
        {
            SelectForShareFrame.add(team);
        }

        SelectForShareFrame.setLocationRelativeTo(GUI_Elements.Window);
        SelectForShareFrame.setVisible(true);

    }

    static public JFrame TeamFileFrame;
    public static void InitializeTeamFileFrame()
    {
        TeamFileFrame = new JFrame();
        TeamFileFrame.setSize(new Dimension(500, 900));
        TeamFileFrame.setLayout(new FlowLayout());

        File directory = new File("src/main/java/org/example/Files/Team/" + Main.current_team.getId());

        File[] files = directory.listFiles();
        int index = 0;

        for(File file : files)
        {
            File_Button file_button = new File_Button(file);
            file_button.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if (Desktop.isDesktopSupported())
                    {
                        try
                        {
                            Desktop.getDesktop().open(file);
                        }
                        catch (IOException ex)
                        {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
            TeamFileFrame.add(file_button, setConstraints(GridBagConstraints.NONE, 1, 1, 1, index));
            index++;
        }

        TeamFileFrame.setLocationRelativeTo(GUI_Elements.Window);
        TeamFileFrame.setVisible(true);
    }

    static public JFrame UserFileFrame;
    public static void InitializeUserFileFrame(String user_name)
    {
        UserFileFrame = new JFrame();
        UserFileFrame.setSize(new Dimension(500, 900));
        UserFileFrame.setLayout(new FlowLayout());

        File directory = new File("src/main/java/org/example/Files/User/" + user_name);

        File[] files = directory.listFiles();
        int index = 0;

        for(File file : files)
        {
            File_Button file_button = new File_Button(file);
            file_button.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if (Desktop.isDesktopSupported())
                    {
                        try
                        {
                            Desktop.getDesktop().open(file);
                        }
                        catch (IOException ex)
                        {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
            UserFileFrame.add(file_button, setConstraints(GridBagConstraints.NONE, 1, 1, 1, index));
            index++;
        }

        UserFileFrame.setLocationRelativeTo(GUI_Elements.Window);
        UserFileFrame.setVisible(true);
    }

    static public JPanel Users_Panel = new JPanel(new GridBagLayout());
    public static void InitializeUsersPanel()
    {
        int index = 0;
        Users_Panel.removeAll();

        Users_Panel.setBackground(Color.PINK);

        ArrayList<User> user_list;
        try
        {
            user_list = Session.SelectAllCustomers();
        }
        catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }

        for(User user : user_list)
        {
            User_Button user_button = new User_Button(user);
            Users_Panel.add(user_button, setConstraints(GridBagConstraints.NONE, 1, 1, 0 ,index));
            index++;
        }

        JButton Return_Menu_Button = new JButton("Return Menu");
        Return_Menu_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Window.getContentPane().removeAll();
                InitializeAdminMenu();
                Window.revalidate();
                Window.repaint();
            }
        });
        Users_Panel.add(Return_Menu_Button, setConstraints(GridBagConstraints.NONE, 1, 1, 0, index));
    }

    static public JPanel Requests_Panel = new JPanel(new GridBagLayout());
    public static void InitializeRequestsPanel()
    {
        int index = 0;
        Requests_Panel.removeAll();

        Requests_Panel.setBackground(Color.PINK);

        ArrayList<Request_Button> requests;
        try
        {
            requests = Session.GetAllRequests();
        }
        catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }

        for(Request_Button request : requests)
        {
            Requests_Panel.add(request, setConstraints(GridBagConstraints.NONE, 1, 1, 0, index));
            index++;
        }

        JButton Return_Menu_Button = new JButton("Return Menu");
        Return_Menu_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Window.getContentPane().removeAll();
                InitializeAdminMenu();
                Window.revalidate();
                Window.repaint();
            }
        });
        Requests_Panel.add(Return_Menu_Button, setConstraints(GridBagConstraints.NONE, 1, 1, 0 ,index));
    }

    static public JPanel Log_Files_Panel = new JPanel(new GridBagLayout());
    public static void InitializeLogFilesPanel()
    {
        int index = 0;
        Log_Files_Panel.removeAll();

        Log_Files_Panel.setBackground(Color.PINK);

        File directory = new File("src/main/java/org/example/Logs");

        File[] files = directory.listFiles();

        for(File file : files) {
            if (!file.getName().endsWith(".lck"))
            {
                JButton file_button = new JButton(file.getName());
                file_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (Desktop.isDesktopSupported()) {
                            try
                            {
                                Desktop.getDesktop().open(file);
                            }
                            catch (IOException ex)
                            {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                });
                Log_Files_Panel.add(file_button, setConstraints(GridBagConstraints.NONE, 1, 1, 0, index));
                index++;
            }
        }

        JButton Return_Menu_Button = new JButton("Return Menu");
        Return_Menu_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Window.getContentPane().removeAll();
                InitializeAdminMenu();
                Window.revalidate();
                Window.repaint();
            }
        });
        Log_Files_Panel.add(Return_Menu_Button, setConstraints(GridBagConstraints.NONE, 1, 1, 0, index));
    }

}
