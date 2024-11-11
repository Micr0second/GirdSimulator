package Yukinohana.GridSimulator.Options;

import javax.swing.JButton;

public class Brush extends JButton
{
    final String[] NAMES = {"Eraser", "Wall", "Laser", "<html>" + "Conveyer" + "<br>" + "Belt" + "</html>", "Potal"};

    private int type;

    public Brush(int type)
    {
        this.type = type;
        setText(NAMES[type]);
        setActionCommand("brush");
    }

    public int getType()
    {
        return type;
    }
}
