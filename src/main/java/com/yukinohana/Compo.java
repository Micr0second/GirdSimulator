package com.yukinohana;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Compo 
{
    final String[] ARROWS = {"↑", "↓", "←", "→"};
    private final Color[] COLORSET = {Color.LIGHT_GRAY, new Color(55,55,55), new Color(255, 55, 55), 
        new Color(64, 81, 90), new Color(45, 117, 240), new Color(240, 133, 39)};
        //wall, laser, convey arrow, potal(1), potal(2)
    private final int X;
    private final int Y;
    private boolean hasDrawn;
    private int step = -1;

    private int type;
    // 0: empty cell, 1: wall, 2: laser, 3; convey, 4: potal
    private int direction = -1;      //if it is convey
    private int[] destination = {-1,-1};    // if it is potal
    private final ArrayList<int[]> DEPS = new ArrayList<>();

    public Compo(int x, int y)
    {
        this.X = x;
        this.Y = y;
    }

    public int getType() {
        return type;
    }

    public void setDirection(int dir)
    {
        direction = dir;
    }

    public int getDirection()
    {
        return direction;
    }

    public void setDestination(int[] dest)
    {
        destination = dest;
    }

    public int[] getDestination()
    {
        return destination;
    }

    public ArrayList<int[]> getDEPS()
    {
        return DEPS;
    }

    public void draw(Graphics g, double cellWidth, double cellHeight) 
    {
        Rectangle2D cell = new Rectangle2D.Double(X * cellWidth, Y * cellHeight, cellWidth, cellHeight);
        //←, →, ↓, ↑
        if(hasDrawn)
        {
            g.setColor(COLORSET[type]);
            Graphics2D newG = (Graphics2D) g;
            newG.fill(cell);
            if(direction != -1)
            {
                g.setColor(Color.WHITE);
                g.drawString(ARROWS[direction],(int) (X * cellWidth + cellWidth/2), (int) (Y * cellHeight + cellHeight/2));
                //System.out.println((int) (x * cellWidth + cellWidth/2) + " " +  (int) (y * cellHeight + cellHeight/2));
            }
            else
            {
                g.drawString("", (int) (X * cellWidth + cellWidth/2), (int) (Y * cellHeight + cellHeight/2));
                //System.out.println((int) (x * cellWidth + cellWidth/2) + " " +  (int) (y * cellHeight + cellHeight/2));
            } 
        }
        else
        {
            g.setColor(COLORSET[0]);
            Graphics2D newG = (Graphics2D) g;
            newG.fill(cell);
        }
    }

    public void setStep(int s)
    {
        step = s;
    }

    public int getStep()
    {
        return step;
    }

    public void makeDarker()
    {
        COLORSET[type] = COLORSET[type].darker();
    }

    public void makeBrighter()
    {
        COLORSET[type] = COLORSET[type].brighter();
    }

    public void setDraw()
    {
        hasDrawn = true;
    }

    public void setType(int type)
    {
        this.type = type;
    }
}
