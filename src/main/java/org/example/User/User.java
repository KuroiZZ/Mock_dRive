package org.example.User;

import org.example.Connection;
import org.example.Main;
import org.example.SessionSystem.Session;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class User
{
    protected String UserId;
    protected String UserName;
    protected String FirstName;
    protected String LastName;
    protected String Password;
    protected Role Role;

    public User(String userId, String userName, String firstName, String lastName, String password)
    {
        UserId = userId;
        UserName = userName;
        FirstName = firstName;
        LastName = lastName;
        Password = password;
        Role = org.example.User.Role.CUSTOMER;
    }

    public void ChangeUserName(){}

    public void SendChangePasswordRequest(){}

    public void UploadFile(){}

    public void CreateTeam(String TeamName, String TeammateName) throws SQLException
    {
        java.sql.Connection connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

        if (Session.UserNameAlreadyExist(TeammateName) && !Objects.equals(this.UserName, TeammateName))
        {
            String querySelect = "SELECT User_Id from user WHERE User_Name = ?";
            PreparedStatement stmt = connection.prepareStatement(querySelect);
            stmt.setString(1, TeammateName);
            ResultSet rs = stmt.executeQuery();
            stmt.clearParameters();

            String Teammate_Id = null;
            while (rs.next())
            {
                Teammate_Id = rs.getString(1);
            }

            if (Teammate_Id != null)
            {
                String query = "INSERT INTO team (Team_Id, Team_Name, Team_Leader) VALUES(?, ?, ?);";
                String Team_Id = UUID.randomUUID().toString();

                stmt = connection.prepareStatement(query);
                stmt.setString(1, Team_Id);
                stmt.setString(2, TeamName);
                stmt.setString(3, this.UserName);
                stmt.execute();

                query = "INSERT INTO team_member (Team_Id, Team_Member) VALUES(?, ?);";

                stmt = connection.prepareStatement(query);
                stmt.setString(1, Team_Id);
                stmt.setString(2, TeammateName);

                stmt.execute();
                stmt.close();
                connection.close();
            }
        }
    }

    public ArrayList<JButton> GetAllTeams() throws SQLException
    {
        java.sql.Connection connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

        ArrayList<JButton> Teams = new ArrayList<JButton>();

        String query = "SELECT Team_Name, Team_Id FROM team WHERE Team_Leader = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, this.UserName);
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
                            Main.current_team = new Team(rs1.getString(1), rs1.getString(2), Session.SelectUser(rs1.getString(3)), user_list);
                            System.out.println(Main.current_team);
                        }


                    }
                    catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            });
            Teams.add(new_button);
        }
        return Teams;
    }

    public void ShareFileWithTeam(){}

    public String getUserId()
    {
        return UserId;
    }

    public String getUserName()
    {
        return UserName;
    }

    public String getFirstName()
    {
        return FirstName;
    }

    public String getLastName()
    {
        return LastName;
    }

    public String getPassword()
    {
        return Password;
    }

    public String getRole() {
        return Role.toString();
    }
}
