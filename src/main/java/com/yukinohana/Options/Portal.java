package com.yukinohana.Options;

import javax.swing.JRadioButton;

public class Portal extends JRadioButton
{
    final String[] NAMES = {"Enter", "Exit"};

    private final int P_TYPE;

    public Portal(int type)
    {
        setEnabled(false);
        this.P_TYPE = type;
        setText(NAMES[type]);
        setActionCommand("portal");
    }

    public int getP_TYPE()
    {
        return P_TYPE;
    }
}
