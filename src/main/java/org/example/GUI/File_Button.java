package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class File_Button extends JButton
{
    private JPanel ContentPanel;
    private JLabel NameLabel;
    private JButton ShareTeamButton;
    private JButton DeleteButton;

    public File_Button(File file)
    {
        super();
        this.setPreferredSize(new Dimension(500, 50));

        this.ContentPanel = new JPanel(new FlowLayout());
        this.ContentPanel.setOpaque(false);

        this.NameLabel = new JLabel(file.getName());
        this.ContentPanel.add(NameLabel);

        this.ShareTeamButton = new JButton("Share");
        this.ShareTeamButton.setBackground(Color.GREEN);
        this.ShareTeamButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GUI_Elements.InitializeSelectForShareFrame(file);
            }
        });
        this.ContentPanel.add(ShareTeamButton);

        this.DeleteButton = new JButton("Delete");
        this.DeleteButton.setBackground(Color.RED);
        this.DeleteButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                file.delete();
                GUI_Elements.File_Panel.removeAll();
                GUI_Elements.InitializeFilePanel();
                GUI_Elements.Window.revalidate();
                GUI_Elements.Window.repaint();
            }
        });
        this.ContentPanel.add(DeleteButton);

        this.add(ContentPanel);
    }

}
