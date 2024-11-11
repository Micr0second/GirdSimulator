package com.yukinohana.JPanels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.yukinohana.Options.BeltDirection;
import com.yukinohana.Options.Brush;
import com.yukinohana.Options.Portal;

public final class OptionPanel extends JPanel implements ActionListener
{
    private Brush brush;    
    //  0: empty cell(eraser), 1: wall, 2: laser, 3; convey, 4: potal
    private final BeltDirection[] DIRECTION = {new BeltDirection(0), new BeltDirection(1), new BeltDirection(2), new BeltDirection(3)};
    // conveyer belt direction
    private final Portal[] PORTALS = {new Portal(0), new Portal(1)};
    // potal enterance and exit
    private final JButton START;
    // start gridSimulation
    private final JButton DEP;
    private final JButton DES;
    private final JButton RESET;

    private DrawPanel drawPanel;
    private final JButton setPortals;

    public OptionPanel()
    {
        setLayout(new GridBagLayout());

        START = new JButton("START");
        START.addActionListener(this);
        START.setActionCommand("start");

        RESET = new JButton("RESET");
        RESET.addActionListener(this);
        RESET.setActionCommand("reset");

        setPortals = new JButton("setPortals");
        setPortals.addActionListener(this);
        setPortals.setActionCommand("setPortals");

        DEP = new JButton("<html>" + "Set" + "<br>" + "Dep" + "</html>");
        DEP.addActionListener(this);
        DEP.setActionCommand("setDep");
        DEP.setHorizontalAlignment(SwingConstants.CENTER);

        DES = new JButton("<html>" + "Set" + "<br>" + "Des" + "</html>");
        DES.addActionListener(this);
        DES.setActionCommand("setDes");
        DES.setHorizontalAlignment(SwingConstants.CENTER);

        addOptions(new GridBagConstraints());

        DIRECTION[0].setSelected(true);
        PORTALS[0].setSelected(true);
    }

    public void addOptions(GridBagConstraints gbc)
    {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 15;

        for(int i = 0; i < 5; i++)
        {
            brush = new Brush(i);
            brush.addActionListener(this);
            gbc.gridwidth = 2;
            if(i == 4)
            {
                i +=3;
            }
            gbc.gridy = i; 
            gbc.gridx = 0;
            this.add(brush, gbc);
            if(i == 3)
            {
                gbc.gridwidth = 1;
                for(int j = 0; j < 4; j++)
                {
                    DIRECTION[j].addActionListener(this);
                    gbc.gridx = j % 2;
                    gbc.gridy = i + 1 + j/2;
                    this.add(DIRECTION[j], gbc);
                }
            }
            if(i == 7)
            {
                gbc.gridwidth = 1;
                for(int j = 0; j < 2; j++)
                {
                    PORTALS[j].addActionListener(this);
                    gbc.gridx = j;
                    gbc.gridy = i + 1;
                    this.add(PORTALS[j], gbc);
                }
            }
        }

        gbc.gridx = 0;
        gbc.gridwidth = 2;

        gbc.gridy = 9;
        this.add(setPortals, gbc);

        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.ipady = 35;
        this.add(DEP, gbc);
        gbc.gridx = 1;
        this.add(DES, gbc);

        gbc.ipady = 25;
        gbc.gridy =11;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        this.add(START, gbc);

        gbc.gridy = 12;
        this.add(RESET, gbc);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        //System.out.println((e.getSource());
        if(e.getActionCommand().equals("brush"))
        {
            //System.out.println((((Brush) e.getSource()).getType()));
            drawPanel.setBurshType(((Brush) e.getSource()).getB_TYPE());
            for(int i = 0; i < 4; i++)
            {
                DIRECTION[i].setEnabled(false);
            } 
            PORTALS[0].setEnabled(false);
            PORTALS[1].setEnabled(false);
            if(drawPanel.getBrushType() == 3)
            {
                for(int i = 0; i < 4; i++)
                {
                    DIRECTION[i].setEnabled(true);
                }
            }
            else if(drawPanel.getBrushType() == 4)
            {
                PORTALS[0].setEnabled(true);
                PORTALS[1].setEnabled(true);
                
            }
            ((Brush) e.getSource()).setBackground(new Color(224, 224, 224));
        }
        if(e.getActionCommand().equals("belt"))
        {
            DIRECTION[drawPanel.getBeltDir()].setSelected(false);
            drawPanel.setBeltDir(((BeltDirection) e.getSource()).getDIRECTION());
            //System.out.println(drawPanel.getBeltDir());
        }
        if(e.getActionCommand().equals("portal"))
        {
            //System.out.println(drawPanel.getPortalType());
            PORTALS[drawPanel.getPortalType()].setSelected(false);
            drawPanel.setPortalType(((Portal) e.getSource()).getP_TYPE());
        }
        if(e.getActionCommand().equals("setPortals"))
        {
            //System.out.println("setPortals");
            drawPanel.setPortals();
            drawPanel.repaint();
        }
        if(e.getActionCommand().equals("start"))
        {
            drawPanel.start();
        }
        if(e.getActionCommand().equals("setDep"))
        {
            drawPanel.setDep();
        }
        if(e.getActionCommand().equals("setDes"))
        {
            drawPanel.setDes();
        }
        if(e.getActionCommand().equals("reset"))
        {
            drawPanel.resetGrid();
        }
    }

    public void setDrawPanel(DrawPanel dp)
    {
        drawPanel = dp;
    }

}
