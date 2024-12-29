package org.example;

import org.example.BackupSystem.SessionAnomalyFinder;
import org.example.GUI.GUI_Elements;
import org.example.Process.Process;
import org.example.SessionSystem.Loggers;
import org.example.User.Team;
import org.example.User.User;

import java.io.IOException;
import java.text.ParseException;

public class Main
{
    static public User current_user;
    static public Team current_team;

    public static void main(String[] args)
    {
        Loggers.InitializeSessionLogger();
        Loggers.InitializePasswordRequestLogger();
        Loggers.InitializeTeamLogger();
        GUI_Elements.InitializeWindowProperties();
        GUI_Elements.InitializeLogInMenu();
        SessionAnomalyFinder.ReadAndCombineSessionLog();
        Process.StartBackup();
        Process.StartSessionAnomaly();
    }
}