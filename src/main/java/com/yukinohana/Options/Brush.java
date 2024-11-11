package com.yukinohana.Options;

import javax.swing.JButton;

public class Brush extends JButton
{
    final String[] NAMES = {"Eraser", "Wall", "Laser", "<html>" + "Conveyer" + "<br>" + "Belt" + "</html>", "Potal"};

    private final int B_TYPE;

    public Brush(int type)
    {
        this.B_TYPE = type;
        setText(NAMES[type]);
        setActionCommand("brush");
    }

    public int getB_TYPE()
    {
        return B_TYPE;
    }
}
