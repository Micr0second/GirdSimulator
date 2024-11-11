package com.yukinohana.Windows;

import com.yukinohana.*;
import com.yukinohana.JPanels.DrawPanel;
import com.yukinohana.JPanels.OptionPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public final class GridWindow
{
    private final JFrame frame;
    private DrawPanel drawPanel;
    private final OptionPanel optionPanel;

    private double cellWidth;
    private double cellHeight;

    public GridWindow(int gW, int gH)
    {
        optionPanel = new OptionPanel();
        drawPanel = new DrawPanel(new Grid(gW, gH));
        optionPanel.setDrawPanel(drawPanel);
        frame = new JFrame("GridPaint");
        frame.addComponentListener(new ComponentAdapter() 
        {
            @Override
            public void componentResized(ComponentEvent componentEvent) 
            {
                cellHeight = (double) drawPanel.getHeight() / gH;
                cellWidth = (double) drawPanel.getWidth() / gW;
                drawPanel.setInterval(cellWidth, cellHeight);
                //System.out.println(drawPanel.getHeight() + " " + drawPanel.getWidth()); 
            }
        });

        frame.setLayout(new GridBagLayout());
        addInterface();

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    public void addInterface()
    {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.weightx = 0.80;
        gbc.weighty = 1;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(25,25,25,25);
        frame.add(drawPanel, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 4;
        gbc.weightx = 0.20;
        gbc.gridwidth = 1;
        gbc.insets.set(0,0,0,0);
        optionPanel.setBackground(Color.WHITE);
        //optionPanel.setMaximumSize(new200, 800);
        frame.add(optionPanel, gbc);
    }
}