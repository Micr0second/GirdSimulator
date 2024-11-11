package Yukinohana.GridSimulator.Options;

import javax.swing.JRadioButton;

public class BeltDirection extends JRadioButton
{
    final String[] NAMES = {"Up", "Down", "Left", "Right"};

    private int dir;

    public BeltDirection(int dir)
    {
        setEnabled(false);
        this.dir = dir;
        setText(NAMES[dir]);
        setActionCommand("belt");
    }

    public int getDir()
    {
        return dir;
    }
 
}
