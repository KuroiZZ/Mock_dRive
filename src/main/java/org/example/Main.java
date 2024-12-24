package org.example;

import org.example.GUI.GUI_Elements;
import org.example.User.Team;
import org.example.User.User;

public class Main
{
    static public User current_user;
    static public Team current_team;

    public static void main(String[] args)
    {
        GUI_Elements.InitializeWindowProperties();
        GUI_Elements.InitializeLogInMenu();
    }
}