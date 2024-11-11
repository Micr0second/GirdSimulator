package Me.GridSimulator.Options;

import javax.swing.JRadioButton;

public class Portal extends JRadioButton
{
    final String[] NAMES = {"Enter", "Exit"};

    private int type;

    public Portal(int type)
    {
        setEnabled(false);
        this.type = type;
        setText(NAMES[type]);
        setActionCommand("portal");
    }

    public int getType()
    {
        return type;
    }
}
