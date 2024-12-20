package org.example.SessionSystem;

import org.example.Connection;
import org.example.User.User;

import java.sql.*;

public class Session
{
    static public void CreateUser(User user) throws SQLException
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

        stmt.close();
        connection.close();

    }

    public void LogIn(){}

    public void LogOut(){}
}
