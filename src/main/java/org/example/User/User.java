package org.example.User;

import org.example.Connection;
import org.example.SessionSystem.Session;

import javax.swing.*;
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
                String query = "INSERT INTO team (Team_Id, Team_Name, Team_Leader, Team_Member) VALUES(?, ?,?,?);";

                stmt = connection.prepareStatement(query);
                stmt.setString(1, UUID.randomUUID().toString());
                stmt.setString(2, TeamName);
                stmt.setString(3, this.UserId);
                stmt.setString(4, Teammate_Id);

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

        String query = "SELECT Team_Name FROM team WHERE Team_Leader = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, this.UserId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next())
        {
            JButton new_button = new JButton(rs.getString(1));
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
