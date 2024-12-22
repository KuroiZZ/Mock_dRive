package org.example;

import org.example.SessionSystem.Session;
import org.example.User.Admin;
import org.example.User.Role;
import org.example.User.User;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args)
    {
        User user1 = new User("3","KuroiZ", "Habil", "Tatarogullari", "495693");

        try
        {
            Session.CreateUser(user1);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}