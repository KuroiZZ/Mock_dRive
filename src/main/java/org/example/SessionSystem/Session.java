package org.example.SessionSystem;

import org.example.Connection;
import org.example.User.Role;
import org.example.User.User;

import java.sql.*;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;

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
        }
        else
        {
            System.out.println("User Already Exist");
        }
    }

    public void LogIn()
    {

    }

    public void LogOut(){}


}
