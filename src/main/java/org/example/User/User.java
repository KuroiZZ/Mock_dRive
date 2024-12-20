package org.example.User;

public class User
{
    private String UserId;
    private String UserName;
    private String FirstName;
    private String LastName;
    private String Password;
    private Role Role;

    public User(String userId, String userName, String firstName, String lastName, String password, org.example.User.Role role)
    {
        UserId = userId;
        UserName = userName;
        FirstName = firstName;
        LastName = lastName;
        Password = password;
        Role = role;
    }

    public void ChangeUserName(){}

    public void SendChangePasswordRequest(){}

    public void UploadFile(){}

    public void SelectTeamMate(){}

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
