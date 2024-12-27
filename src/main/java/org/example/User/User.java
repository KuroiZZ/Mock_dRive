package org.example.User;

import org.example.Connection;
import org.example.GUI.GUI_Elements;
import org.example.SessionSystem.Loggers;
import org.example.Main;
import org.example.SessionSystem.Session;

import java.io.File;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

import static org.example.GUI.GUI_Elements.InitializeProfilePanel;

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

    public void ChangeUserName(String Previous_User_Name, String New_User_Name)
    {
        if (Objects.equals(this.UserName, Previous_User_Name))
        {
            java.sql.Connection connection = null;
            try
            {
                connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

                String query = "UPDATE user SET User_Name = ? WHERE User_Name = ?";

                PreparedStatement stmt = connection.prepareStatement(query);

                stmt.setString(1, New_User_Name);
                stmt.setString(2, Previous_User_Name);

                stmt.execute();


                query = "UPDATE team SET Team_Leader = ? WHERE Team_Leader = ?";

                stmt = connection.prepareStatement(query);

                stmt.setString(1, New_User_Name);
                stmt.setString(2, Previous_User_Name);

                stmt.execute();


                query = "UPDATE team_member SET Team_Member = ? WHERE Team_Member = ?";

                stmt = connection.prepareStatement(query);

                stmt.setString(1, New_User_Name);
                stmt.setString(2, Previous_User_Name);

                stmt.execute();

                stmt.close();
                connection.close();
                Main.current_user = Session.SelectUser(New_User_Name);
                GUI_Elements.Profile_Panel.removeAll();
                InitializeProfilePanel();
            }
            catch (SQLException ex)
            {
                throw new RuntimeException(ex);
            }
        }
    }

    public void SendChangePasswordRequest()
    {
        try
        {
            if(!Session.PasswordRequestAlreadyExist(this.UserId))
            {
                java.sql.Connection connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

                String query = "INSERT INTO password_request (Requested_User_Id, Confirmed) VALUES(?,?)";

                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, this.UserId);
                stmt.setBoolean(2, false);

                stmt.execute();
                stmt.close();
                connection.close();

                String logMessage = "User " +  this.UserName + " send password change request";
                Loggers.password_request_logger.info(logMessage);
            }
            else
            {
                String logMessage = "User " +  this.UserName + " send password change request";
                Loggers.password_request_logger.info(logMessage);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void CreateTeam(String TeamName, String TeammateName)
    {
        try
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
                if (rs.next())
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

                    File directory = new File("src/main/java/org/example/Files/Team/" + Team_Id);
                    directory.mkdirs();
                }
            }
            else
            {
                System.out.println("Invalid user name");
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void AddTeamMate(String TeamId,String TeamMate)
    {
        try
        {
            if (Session.UserNameAlreadyExist(TeamMate) && Session.TeamAlreadyExist(TeamId) &&
                    !Objects.equals(this.UserName, TeamMate) && !Session.UserAlreadyInTeam(TeamMate, TeamId))
            {
                java.sql.Connection connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

                String query = "INSERT INTO team_member (Team_Id, Team_Member) VALUES(?, ?);";

                PreparedStatement stmt = connection.prepareStatement(query);

                stmt = connection.prepareStatement(query);
                stmt.setString(1, TeamId);
                stmt.setString(2, TeamMate);

                stmt.execute();
                stmt.close();
                connection.close();

                String logMessage = "User " +  Main.current_user.getUserName() + " selected " + TeamMate + "as a team mate";
                Loggers.team_logger.info(logMessage);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
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

    public String getRole()
    {
        return Role.toString();
    }
}
