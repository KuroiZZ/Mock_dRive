package org.example.User;

public class Admin extends User
{
    public Admin(String userId, String userName, String firstName, String lastName, String password, org.example.User.Role role)
    {
        super(userId, userName, firstName, lastName, password, role);
    }

    public void ChangeSelectedUserStorageLimit(){}

    public void ResponseUserChangePasswordRequest(){}

    public void TakeUserInformation(){}

    public void ChangeUserInformation(){}
}
