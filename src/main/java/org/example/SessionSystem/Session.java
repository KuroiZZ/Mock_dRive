package org.example.SessionSystem;

import org.example.Connection;
import org.example.GUI.GUI_Elements;
import org.example.Main;
import org.example.User.Admin;
import org.example.User.Role;
import org.example.User.Team;
import org.example.User.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;

public class Session
{
    static public boolean UserIdAlreadyExist(String User_Id) throws SQLException
    {
        java.sql.Connection connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

        String querySelect = "SELECT COUNT(User_Id) from user WHERE User_Id = ?";

        PreparedStatement stmt = connection.prepareStatement(querySelect);

        stmt.setString(1, User_Id);

        ResultSet rs = stmt.executeQuery();

        while (rs.next())
        {
            if(rs.getInt(1) > 0)
            {
                stmt.close();
                connection.close();
                return true;
            }
        }
        stmt.close();
        connection.close();
        return false;
    }

    static public boolean UserNameAlreadyExist(String User_Name) throws SQLException
    {
        java.sql.Connection connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

        String querySelect = "SELECT COUNT(User_Name) from user WHERE User_Name = ?";

        PreparedStatement stmt = connection.prepareStatement(querySelect);

        stmt.setString(1, User_Name);

        ResultSet rs = stmt.executeQuery();

        while (rs.next())
        {
            if(rs.getInt(1) > 0)
            {
                stmt.close();
                connection.close();
                return true;
            }
        }
        stmt.close();
        connection.close();
        return false;
    }

    static public boolean TeamAlreadyExist(String Team_Id) throws SQLException
    {
        java.sql.Connection connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

        String querySelect = "SELECT COUNT(Team_Id) from team WHERE Team_Id = ?";

        PreparedStatement stmt = connection.prepareStatement(querySelect);

        stmt.setString(1, Team_Id);

        ResultSet rs = stmt.executeQuery();

        while (rs.next())
        {
            if(rs.getInt(1) > 0)
            {
                stmt.close();
                connection.close();
                return true;
            }
        }
        stmt.close();
        connection.close();
        return false;
    }

    static public boolean UserAlreadyInTeam(String User_Name, String Team_Id) throws SQLException
    {
        java.sql.Connection connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

        String querySelect = "SELECT COUNT(Team_Id) from team_member WHERE Team_Id = ? and Team_Member = ?";

        PreparedStatement stmt = connection.prepareStatement(querySelect);

        stmt.setString(1, Team_Id);
        stmt.setString(2, User_Name);

        ResultSet rs = stmt.executeQuery();

        while (rs.next())
        {
            if(rs.getInt(1) > 0)
            {
                stmt.close();
                connection.close();
                return true;
            }
        }
        stmt.close();
        connection.close();
        return false;
    }

    static public boolean PasswordRequestAlreadyExist(String User_Id) throws SQLException {
        java.sql.Connection connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

        String querySelect = "SELECT COUNT(Requested_User_Id) from password_request WHERE Requested_User_Id = ?";

        PreparedStatement stmt = connection.prepareStatement(querySelect);

        stmt.setString(1, User_Id);

        ResultSet rs = stmt.executeQuery();

        while (rs.next())
        {
            if(rs.getInt(1) > 0)
            {
                stmt.close();
                connection.close();
                return true;
            }
        }
        stmt.close();
        connection.close();
        return false;
    }

    static public boolean PasswordRequestAccepted(String User_Id) throws SQLException
    {
        if (PasswordRequestAlreadyExist(User_Id))
        {
            java.sql.Connection connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

            String querySelect = "SELECT Confirmed from password_request WHERE Requested_User_Id = ?";

            PreparedStatement stmt = connection.prepareStatement(querySelect);

            stmt.setString(1, User_Id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                return rs.getBoolean(1);
            }
        }
        return false;
    }

    static public String  EncryptPassword(String Password)
    {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(Password, salt);
    }

    static public void Register(String User_Name, String First_Name, String Last_Name, String Password) throws SQLException
    {

        String User_Id = UUID.randomUUID().toString();
        String Encrypted_Password = EncryptPassword(Password);

        if (!UserNameAlreadyExist(User_Name) && !UserIdAlreadyExist(User_Id))
        {
            java.sql.Connection connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

            String query = "INSERT INTO user (User_Id, User_Name, First_Name, Last_Name, Password, Role) VALUES(?,?,?,?,?,?);";

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, User_Id);
            stmt.setString(2, User_Name);
            stmt.setString(3, First_Name);
            stmt.setString(4, Last_Name);
            stmt.setString(5, Encrypted_Password);
            stmt.setString(6, Role.CUSTOMER.toString());

            stmt.execute();
            System.out.println("User Added Successfully");
            stmt.close();
            connection.close();

            File directory = new File("src/main/java/org/example/Files/User/" + User_Name);
            directory.mkdirs();
        }
        else
        {
            System.out.println("User Already Exist");
        }
    }

    static public User LogIn(String User_Name, String Password) throws SQLException
    {
        java.sql.Connection connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

        if (!UserNameAlreadyExist(User_Name))
        {
            System.out.println("User name or password is incorrect");
            return null;
        }

        String querySelect = "SELECT * from user WHERE User_Name = ?";
        PreparedStatement stmt = connection.prepareStatement(querySelect);
        stmt.setString(1, User_Name);
        ResultSet rs = stmt.executeQuery();

        User logged_user = null;


        while (rs.next())
        {
            if(BCrypt.checkpw(Password, rs.getString(5)))
            {
                if (Objects.equals(rs.getString(6), "CUSTOMER"))
                {
                    logged_user = new User(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5));
                }
                else
                {
                    logged_user = new Admin(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5));
                }
                System.out.println("User logged in");
            }
            else
            {
                System.out.println("User name or password is incorrect");
            }
        }


        stmt.close();
        connection.close();

        return logged_user;
    }

    static public User SelectUser(String User_Name) throws SQLException
    {
        java.sql.Connection connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

        String querySelect = "SELECT * from user WHERE User_Name = ?";
        PreparedStatement stmt = connection.prepareStatement(querySelect);
        stmt.setString(1, User_Name);
        ResultSet rs = stmt.executeQuery();

        User logged_user = null;

        while (rs.next())
        {
                if (Objects.equals(rs.getString(6), "CUSTOMER"))
                {
                    logged_user = new User(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5));
                }
                else
                {
                    logged_user = new Admin(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5));
                }
        }
        return logged_user;
    }

    static public ArrayList<JButton> GetAllTeams(String Team_Leader) throws SQLException
    {
        java.sql.Connection connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

        ArrayList<JButton> Teams = new ArrayList<JButton>();

        String query = "SELECT Team_Name, Team_Id FROM team WHERE Team_Leader = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, Team_Leader);
        ResultSet rs = stmt.executeQuery();
        while (rs.next())
        {
            JButton new_button = new JButton(rs.getString(1));
            String Team_Id = rs.getString(2);
            new_button.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        Main.current_team = SelectTeam(Team_Id);
                    }
                    catch (SQLException ex)
                    {
                        throw new RuntimeException(ex);
                    }
                    GUI_Elements.Content_Panel.remove(GUI_Elements.File_Panel);
                    GUI_Elements.Team_Panel.removeAll();
                    GUI_Elements.InitializeTeamPanel();
                    GUI_Elements.Content_Panel.add(GUI_Elements.Team_Panel, GUI_Elements.setConstraints(GridBagConstraints.BOTH,1,1,0,0));
                    GUI_Elements.Window.revalidate();
                    GUI_Elements.Window.repaint();
                }
            });
            Teams.add(new_button);
        }
        return Teams;
    }

    static public Team SelectTeam(String Team_Id) throws SQLException
    {
        java.sql.Connection connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

        ArrayList<User> user_list = new ArrayList<>();

        String query1 = "SELECT * FROM team WHERE Team_Id = ?";
        PreparedStatement stmt1 = connection.prepareStatement(query1);
        stmt1.setString(1, Team_Id);
        ResultSet rs1 = stmt1.executeQuery();
        if (rs1.next())
        {
            System.out.println(rs1.getString(3));
            String query2 = "SELECT Team_Member FROM team_member WHERE Team_Id = ?";
            PreparedStatement stmt2 = connection.prepareStatement(query2);
            stmt2.setString(1, Team_Id);
            ResultSet rs2 = stmt2.executeQuery();
            while (rs2.next())
            {
                User new_user = Session.SelectUser(rs2.getString(1));
                user_list.add(new_user);
            }
        }
        return new Team(rs1.getString(1), rs1.getString(2), Session.SelectUser(rs1.getString(3)), user_list);
    }

    static public void LogOut()
    {
        Main.current_user = null;
        Main.current_team = null;
        GUI_Elements.Window.getContentPane().removeAll();
        GUI_Elements.Profile_Panel.removeAll();
        GUI_Elements.Create_Team_Panel.removeAll();
        GUI_Elements.File_Panel.removeAll();
        GUI_Elements.Settings_Panel.removeAll();
        GUI_Elements.Change_User_Name_Panel.removeAll();
        GUI_Elements.Team_Panel.removeAll();
        GUI_Elements.InitializeLogInMenu();
    }


}
