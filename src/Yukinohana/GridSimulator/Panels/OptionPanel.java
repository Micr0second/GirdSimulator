package Yukinohana.GridSimulator.Panels;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Yukinohana.GridSimulator.Options.BeltDirection;
import Yukinohana.GridSimulator.Options.Brush;
import Yukinohana.GridSimulator.Options.Portal;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class OptionPanel extends JPanel implements ActionListener
{
    final Insets DEF = new Insets(20,20,10,10);

    private Brush brush;    
    //  0: empty cell(eraser), 1: wall, 2: laser, 3; convey, 4: potal
    private BeltDirection[] direction = {new BeltDirection(0), new BeltDirection(1), new BeltDirection(2), new BeltDirection(3)};
    // conveyer belt direction
    private Portal[] portal = {new Portal(0), new Portal(1)};
    // potal enterance and exit
    private JButton start;
    // start gridSimulation
    private JButton selectDep;
    private JButton selectDes;
    private JButton reset;

    private DrawPanel drawPanel;
    private JButton setPortals;

    public OptionPanel()
    {
        setLayout(new GridBagLayout());

        start = new JButton("START");
        start.addActionListener(this);
        start.setActionCommand("start");

        reset = new JButton("RESET");
        reset.addActionListener(this);
        reset.setActionCommand("reset");

        setPortals = new JButton("setPortals");
        setPortals.addActionListener(this);
        setPortals.setActionCommand("setPortals");

        selectDep = new JButton("<html>" + "Set" + "<br>" + "Dep" + "</html>");
        selectDep.addActionListener(this);
        selectDep.setActionCommand("setDep");
        selectDep.setHorizontalAlignment(SwingConstants.CENTER);

        selectDes = new JButton("<html>" + "Set" + "<br>" + "Des" + "</html>");
        selectDes.addActionListener(this);
        selectDes.setActionCommand("setDes");
        selectDes.setHorizontalAlignment(SwingConstants.CENTER);

        addOptions(new GridBagConstraints());

        direction[0].setSelected(true);
        portal[0].setSelected(true);
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
                    direction[j].addActionListener(this);
                    gbc.gridx = j % 2;
                    gbc.gridy = i + 1 + j/2;
                    this.add(direction[j], gbc);
                }
            }
            if(i == 7)
            {
                gbc.gridwidth = 1;
                for(int j = 0; j < 2; j++)
                {
                    portal[j].addActionListener(this);
                    gbc.gridx = j;
                    gbc.gridy = i + 1;
                    this.add(portal[j], gbc);
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
        this.add(selectDep, gbc);
        gbc.gridx = 1;
        this.add(selectDes, gbc);

        gbc.ipady = 25;
        gbc.gridy =11;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        this.add(start, gbc);

        gbc.gridy = 12;
        this.add(reset, gbc);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        //System.out.println((e.getSource());
        if(e.getActionCommand().equals("brush"))
        {
            //System.out.println((((Brush) e.getSource()).getType()));
            drawPanel.setBurshType(((Brush) e.getSource()).getType());
            for(int i = 0; i < 4; i++)
            {
                direction[i].setEnabled(false);
            } 
            portal[0].setEnabled(false);
            portal[1].setEnabled(false);
            if(drawPanel.getBrushType() == 3)
            {
                for(int i = 0; i < 4; i++)
                {
                    direction[i].setEnabled(true);
                }
            }
            else if(drawPanel.getBrushType() == 4)
            {
                portal[0].setEnabled(true);
                portal[1].setEnabled(true);
                
            }
            ((Brush) e.getSource()).setBackground(new Color(224, 224, 224));
        }
        if(e.getActionCommand().equals("belt"))
        {
            direction[drawPanel.getBeltDir()].setSelected(false);
            drawPanel.setBeltDir(((BeltDirection) e.getSource()).getDir());
            //System.out.println(drawPanel.getBeltDir());
        }
        if(e.getActionCommand().equals("portal"))
        {
            //System.out.println(drawPanel.getPortalType());
            portal[drawPanel.getPortalType()].setSelected(false);
            drawPanel.setPortalType(((Portal) e.getSource()).getType());
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
        }
    }

    public void setDrawPanel(DrawPanel dp)
    {
        drawPanel = dp;
    }

}
