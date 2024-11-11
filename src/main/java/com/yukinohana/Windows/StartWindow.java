package com.yukinohana.Windows;

import java.awt.event.*;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class StartWindow implements ActionListener
{
    private final JFrame frame;
    private final JLabel lWidth, lHeight, size;
    private final JTextField width;
    private final JTextField height;
    private final JButton enter;

    public StartWindow(String title)
    {
        frame = new JFrame(title);
        width = new JTextField("0", SwingConstants.CENTER);

        frame.setSize(250, 300);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);  

        width.setBounds(75, 80, 100, 30);

        lWidth = new JLabel("Size of width", SwingConstants.CENTER);
        lWidth.setHorizontalAlignment(JLabel.CENTER);
        lWidth.setBounds(75, 55, 100, 30);

        height = new JTextField("0", SwingConstants.CENTER);
        height.setBounds(75, 140, 100, 30);

        lHeight = new JLabel("Size of height", SwingConstants.CENTER);
        lHeight.setHorizontalAlignment(JLabel.CENTER);
        lHeight.setBounds(75, 115, 100, 30);

        size = new JLabel("Enter your size of grid", SwingConstants.CENTER);
        size.setBounds(25, 15, 200, 30);

        enter = new JButton("Enter");
        enter.setHorizontalAlignment(JButton.CENTER);
        enter.setBounds(100, 185, 50, 40);
        enter.setActionCommand("set grid");
        enter.addActionListener(this);

        frame.add(size);
        frame.add(enter);
        frame.add(width);
        frame.add(height);
        frame.add(lWidth);
        frame.add(lHeight);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getActionCommand().equals("set grid"))
        {
            if(isTextValid(height) && isTextValid(width))
            {
                new GridWindow(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));
                frame.dispose();
            }
        }
        else
        {

        }
    }

    private boolean isTextValid(JTextField height2) 
    {
        try
        {
            Integer.valueOf(height2.getText());
            return true;
        }
        catch(NumberFormatException e)
        {
            height2.setText("type number");
        }
        return false;
    }
}
