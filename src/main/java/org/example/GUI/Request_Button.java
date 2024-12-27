package org.example.GUI;

import org.example.Connection;
import org.example.SessionSystem.Loggers;
import org.example.User.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Request_Button extends JPanel
{
    private JPanel ContentPanel;
    private JLabel NameLabel;
    private JButton AcceptButton;
    private JButton RefuseButton;

    public Request_Button(User user)
    {
        super();
        this.setSize(new Dimension(250, 50));

        this.ContentPanel = new JPanel(new FlowLayout());
        this.ContentPanel.setOpaque(false);

        this.NameLabel = new JLabel(user.getUserName());
        this.ContentPanel.add(NameLabel);

        this.AcceptButton = new JButton("Accept");
        this.AcceptButton.setBackground(Color.GREEN);
        this.AcceptButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                java.sql.Connection connection = null;
                try
                {
                    connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

                    String query = "UPDATE password_request SET Confirmed = ? WHERE Requested_User_Id = ?";

                    PreparedStatement stmt = connection.prepareStatement(query);

                    stmt.setBoolean(1, true);
                    stmt.setString(2, user.getUserId());

                    stmt.execute();

                    GUI_Elements.InitializeRequestsPanel();
                    GUI_Elements.Content_Panel.removeAll();
                    GUI_Elements.Content_Panel.add(GUI_Elements.Requests_Panel);
                    GUI_Elements.Window.revalidate();
                    GUI_Elements.Window.repaint();

                    String log = "User " + user.getUserName() + "'s password request accepted";
                    Loggers.password_request_logger.info(log);
                }
                catch (SQLException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
        });
        this.ContentPanel.add(AcceptButton);

        this.RefuseButton = new JButton("Refuse");
        this.RefuseButton.setBackground(Color.RED);
        this.RefuseButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                java.sql.Connection connection = null;
                try
                {
                    connection = (java.sql.Connection) DriverManager.getConnection(Connection.url, Connection.user, Connection.password);

                    String query = "DELETE FROM password_request WHERE Requested_User_Id = ?";

                    PreparedStatement stmt = connection.prepareStatement(query);

                    stmt.setString(1, user.getUserId());

                    stmt.execute();

                    GUI_Elements.InitializeRequestsPanel();
                    GUI_Elements.Content_Panel.removeAll();
                    GUI_Elements.Content_Panel.add(GUI_Elements.Requests_Panel);
                    GUI_Elements.Window.revalidate();
                    GUI_Elements.Window.repaint();

                    String log = "User " + user.getUserName() + "'s password request rejected";
                    Loggers.password_request_logger.info(log);
                }
                catch (SQLException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
        });
        this.ContentPanel.add(RefuseButton);

        this.add(ContentPanel);
    }
}
