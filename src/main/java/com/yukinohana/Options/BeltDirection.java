package com.yukinohana.Options;

import javax.swing.JRadioButton;

public class BeltDirection extends JRadioButton
{
    final String[] NAMES = {"UP", "DOWN", "LEFT", "RIGHT"};

    private final int DIRECTION; //DIR should be one of four direction

    public BeltDirection(int dir)
    {
        setEnabled(false);
        this.DIRECTION = dir;
        setText(NAMES[dir]); 
        setActionCommand("belt");
    }

    public int getDIRECTION()
    {
        return DIRECTION;
    }
 
}
