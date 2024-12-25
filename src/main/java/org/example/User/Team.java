package org.example.User;

import java.util.ArrayList;

public class Team
{
    private String Id;
    private String Name;
    private User Team_Leader;
    private ArrayList<User> Team_Members;

    public Team(String id, String name, User team_Leader, ArrayList<User> team_Members)
    {
        Id = id;
        Name = name;
        Team_Leader = team_Leader;
        Team_Members = team_Members;
    }

    public String getId()
    {
        return Id;
    }

    public String getName()
    {
        return Name;
    }

    public User getTeam_Leader()
    {
        return Team_Leader;
    }

    public ArrayList<User> getTeam_Members()
    {
        return Team_Members;
    }
}
