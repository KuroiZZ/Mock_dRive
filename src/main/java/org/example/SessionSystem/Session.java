package org.example.SessionSystem;

import org.example.Connection;
import org.example.User.User;

import java.sql.*;

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

    static public void CreateUser(User user) throws SQLException
    {
        if (!UserNameAlreadyExist(user.getUserName()) && !UserIdAlreadyExist(user.getUserId()))
        {
            java.sql.Connection connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

            String query = "INSERT INTO user (User_Id, User_Name, First_Name, Last_Name, Password, Role) VALUES(?,?,?,?,?,?);";

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, user.getUserId());
            stmt.setString(2, user.getUserName());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getRole());

            stmt.execute();
            System.out.println("User Added Successfully");
            stmt.close();
            connection.close();
        }
        else
        {
            System.out.println("User Already Exist");
        }


    }

    public void LogIn(){}

    public void LogOut(){}
}
