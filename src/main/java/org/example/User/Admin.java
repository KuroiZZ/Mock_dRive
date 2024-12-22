package org.example.User;

public class Admin extends User
{
    public Admin(String userId, String userName, String firstName, String lastName, String password)
    {
        super(userId, userName, firstName, lastName, password);
        this.Role = org.example.User.Role.ADMIN;
    }

    public void ChangeSelectedUserStorageLimit(){}

    public void ResponseUserChangePasswordRequest(){}

    public void TakeUserInformation(){}

    public void ChangeUserInformation(){}
}
