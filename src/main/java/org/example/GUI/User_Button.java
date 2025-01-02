package org.example.GUI;

import org.example.User.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class User_Button extends JPanel
{
    private JPanel ContentPanel;
    private JLabel NameLabel;
    private JButton ShowInformationButton;
    private JButton ShowFilesButton;
    private JButton ShowTeamsButton;
    private JButton DeleteUserButton;

    public User_Button(User user)
    {
        super();
        this.setSize(new Dimension(250, 50));

        this.ContentPanel = new JPanel(new FlowLayout());
        this.ContentPanel.setOpaque(false);

        this.NameLabel = new JLabel(user.getUserName());
        this.ContentPanel.add(NameLabel);

        this.ShowInformationButton = new JButton("Show Information");
        this.ShowInformationButton.setBackground(Color.GREEN);
        this.ShowInformationButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String userInfo = "<html>"
                        + "User Name: " + user.getUserName() + "<br>"
                        + "First Name: " + user.getFirstName() + "<br>"
                        + "Last Name: " + user.getLastName() + "<br>"
                        + "Password: " + user.getPassword() + "<br>"
                        + "Role: " + user.getRole().toString()
                        + "</html>";

                JOptionPane.showMessageDialog(GUI_Elements.Window, userInfo, "User Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        this.ContentPanel.add(ShowInformationButton);

        this.ShowFilesButton = new JButton("Show Files");
        this.ShowFilesButton.setBackground(Color.GREEN);
        this.ShowFilesButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GUI_Elements.InitializeUserFileFrame(user.getUserName());
            }
        });
        this.ContentPanel.add(ShowFilesButton);

        this.ShowTeamsButton = new JButton("Show Teams");
        this.ShowTeamsButton.setBackground(Color.GREEN);
        this.ShowTeamsButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GUI_Elements.InitializeUserTeamFrame(user.getUserName());
            }
        });
        this.ContentPanel.add(ShowTeamsButton);


        this.add(ContentPanel);
    }
}
