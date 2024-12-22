package org.example.User;

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
