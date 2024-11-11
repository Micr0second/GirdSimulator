package Yukinohana.GridSimulator.Panels;

import Yukinohana.GridSimulator.Grid;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import javax.swing.JPanel;

public class DrawPanel extends JPanel
{
    private double cellWidth;
    private double cellHeight;

    private int brushType;
    private int beltDir;
    private int portalType;

    private Grid grid;

    private boolean setP;
    private double[] pStart, pEnd = null;
    private int[] des, dep;
    private boolean setDs, setDp;

    public DrawPanel(Grid grid)
    {
        setBackground(Color.LIGHT_GRAY);
        //setOpaque(true);
        this.grid = grid;
        cellHeight = (int) getHeight() / grid.getHeight();
        cellWidth = (int) getWidth() / grid.getWidth();
        addMouseMotionListener(new MouseAdapter() {
            
            @Override
            public void mouseDragged(MouseEvent e) 
            {
                //System.out.println(e.getY() + " " + e.getX());
                //System.out.println(cellHeight + " " + cellWidth);
                int newY = (int) (e.getY() / cellHeight);
                int newX = (int) (e.getX() / cellWidth);
                if(setP && ! setDp && !setDs)
                {
                    if(pStart == null)
                    {
                        if(grid.get(newY, newX).getType() == 4)
                        {
                            pStart = new double[] {(e.getY() / cellHeight), (e.getX() / cellWidth)};
                            //System.out.println(pStart[0] + ", " + pStart[1]);
                        }
                    }
                    if(pStart != null)
                    {
                        pEnd = new double[] {(e.getY() / cellHeight), (e.getX() / cellWidth)};
                        repaint();
                        //System.out.println(pStart[0] + ", " + pStart[1] + " : " + pEnd[0] + ", " + pEnd[1]);
                    }
                }
                else if(isValidNum(newY, newX) && ! setDp && !setDs)
                {
                    if(brushType == 3)
                    {
                        grid.get(newY, newX).setDirection(beltDir);
                    }
                    else
                    {
                        grid.get(newY, newX).setDirection(-1);
                    }
                    if(brushType == 4 && portalType == 1)
                    {
                        brushType = 5;
                    }
                    else if(brushType == 5 && portalType == 0)
                    {
                        brushType = 4;
                    }
                    grid.get(newY, newX).setType(brushType);
                    grid.get(newY, newX).setDraw();
                    repaint();
                }
            }
        });
        addMouseListener(new MouseAdapter(){

            @Override
            public void mouseReleased(MouseEvent e)
            {
                //System.out.println("mouseReleased");
                int newY = (int) (e.getY() / cellHeight);
                int newX = (int) (e.getX() / cellWidth);

                if(setP && pStart != null)
                {
                    if(grid.get((int) pEnd[0], (int) pEnd[1]).getType() == 5)
                    {
                        grid.get((int) pStart[0], (int) pStart[1]).setDestination(new int[] {(int)pEnd[0], (int)pEnd[1]});
                        System.out.println(pStart[0] + ", " + pStart[1] + " : " + pEnd[0] + ", " + pEnd[1]);
                        repaint();
                    }
                    pStart = null;
                    pEnd = null;
                }
                if(setDp && grid.get(newY, newX).getType() == 0)
                {
                    dep = new int[] {newY, newX};
                    setDp = false;
                    repaint();
                    System.out.println(newY + " " + newX);
                }
                if(setDs && grid.get(newY, newX).getType() == 0)
                {
                    des = new int[] {newY, newX};
                    setDs = false;
                    repaint();
                    System.out.println(newY + " " + newX);
                }
            }
        });
    }

    public void paint(Graphics g)
    {
        for(int r = 0; r < grid.getHeight(); r++)
        {
            for(int c = 0; c<grid.getWidth(); c++) 
            {
                grid.get(r,c).draw(g, cellWidth, cellHeight);
            }
        }
        Graphics2D newG = (Graphics2D) g;
        if(setP && pStart != null)
        {
            newG.setStroke(new BasicStroke(5));
            newG.setColor(Color.BLACK);
            //System.out.println(pStart[0] + ", " + pStart[1] + " : " + pEnd[0] + " : " +  pEnd[1]);
            Line2D line = new Line2D.Double(pStart[1] * cellWidth,pStart[0] * cellHeight ,pEnd[1] * cellWidth ,pEnd[0] * cellHeight);
            newG.draw(line);
        }
        if(des != null)
        {
            Rectangle2D rDes = new Rectangle2D.Double(des[1] * cellWidth + cellWidth * 0.25, des[0] * cellHeight + cellHeight * 0.25, cellWidth * 0.5, cellHeight * 0.5);
            newG.setColor(Color.BLACK);
            newG.fill(rDes);
        }
        if(dep != null)
        {
            Rectangle2D rDep = new Rectangle2D.Double(dep[1] * cellWidth + cellWidth * 0.25, dep[0] * cellHeight + cellHeight * 0.25, cellWidth * 0.5, cellHeight * 0.5);
            newG.setColor(Color.BLACK);
            newG.fill(rDep);
        }
    }
    
    public void setInterval(double cX, double cY)
    {
        cellHeight = cY;
        cellWidth = cX;
    }

    public boolean isValidNum(int height, int width)
    {
        if(height < grid.getHeight() && width < grid.getWidth() && height > -1 && width > -1
            /*&& !(height == des[0] && width == des[1]) && !(height == dep[0] && width == dep[1]) */)
        {
            return true;
        }
        return false;
    }

    public void setPortals()
    {
        setP = !setP;
        //System.out.println(setP);
        grid.highlightP(setP);
        repaint();
    }

    public Grid getGrid()
    {
        return grid;
    }

    public void setBurshType(int type)
    {
        brushType = type;
    }

    public int getBrushType()
    {
        return brushType;
    }

    public int getBeltDir()
    {
        return beltDir;
    }

    public void setBeltDir(int bd)
    {
        beltDir = bd;
    }

    public int getPortalType()
    {
        return portalType;
    }

    public void setPortalType(int pt)
    {
        portalType = pt;
    }

    public void setDes()
    {
        setDs = !setDs;
        setDp = false;
    }

    public void setDep()
    {
        setDp = !setDp;
        setDs = false;
    }

    public void start()
    {
        grid.pathFind(dep[0], dep[1], des[0], des[1]);  //test Line
        grid.setPath(des[0], des[1]);
        pathTest();
    }

    public void pathTest()
    {
        LinkedList<int[]> temp = new LinkedList<>(grid.getPath());
        //System.out.println(temp.size());
        while(!temp.isEmpty())
        {
            int[] cPos = temp.removeFirst();
            //System.out.println(cPos[0] + ", " + cPos[1]);
            grid.get(cPos[0], cPos[1]).makeDarker();
        }
        repaint();
    }
}
