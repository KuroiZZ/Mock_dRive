package org.example;

import org.example.GUI.GUI_Elements;
import org.example.User.User;

public class Main
{
    static public User current_user;

    public static void main(String[] args)
    {
        GUI_Elements.InitializeWindowProperties();
        GUI_Elements.InitializeLogInMenu();
    }
}