package Yukinohana.GridSimulator;

import java.util.LinkedList;

public class Grid 
{
    final int[] XPOS = {0,0,-1,1};
    final int[] YPOS = {-1,1,0,0};
    //up, down, left, right
    final int[] REXPOS = {0,0,1,-1};
    final int[] REYPOS = {1,-1,0,0}; 

    private int width, height;
    private Compo[][] grid;
    private LinkedList<int[]> path;

    public Grid(int w, int h)
    {
        width = w;
        height = h;
        grid = new Compo[height][width];

        for(int r = 0; r < height; r ++)
        {
            for(int c = 0; c < width; c++)
            {
                grid[r][c] = new Compo(c, r); 
            }
        }
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Compo[][] getComps()
    {
        return grid;
    }

    public Compo get(int y, int x)
    {
        if(y < height && x < width)
        {
            return grid[y][x]; 
        }
        return null;
    }

    public void highlightP(boolean setP)
    {
        for(int r = 0;r < height; r++)
        {
            for(int c = 0; c < width; c++)
            {
                if(get(r, c).getType() < 4)
                {
                    if(setP)
                    {
                        get(r,c).makeDarker();
                    }
                    else
                    {
                        get(r,c).makeBrighter();
                    }
                    
                }
            }
        }
    }

    public void pathFind(int startR, int startC, int endR, int endC)
    {
        LinkedList<int[]> que = new LinkedList<int[]>();
        LinkedList<Integer> steps = new LinkedList<Integer>();
        que.add(new int[] {startR, startC});
        steps.add(0);
        get(startR, startC).setStep(0);

        while(!que.isEmpty())
        {
            int[] currentPos = que.removeFirst();
            int currentStep = steps.removeFirst();

            if(get(currentPos[0], currentPos[1]).getType() == 3)
            {
                Compo newCom = get(currentPos[0], currentPos[1]);
                int direction = newCom.getDirection();
                int[] tempC = new int[] {currentPos[0] + YPOS[direction], currentPos[1] + XPOS[direction]};
                if(tempC[0] > -1 && tempC[0] < height && tempC[1] > -1 && tempC[1] < width && get(tempC[0], tempC[1]).getStep() < 0)
                {
                    get(tempC[0], tempC[1]).setStep(currentStep);
                    que.add(tempC);
                    steps.add(currentStep);
                }
                continue;
            }
            for(int i = 0; i < 4; i++)
            {
                int[] newPos = {currentPos[0] + YPOS[i], currentPos[1] + XPOS[i]};
                //System.out.println(newPos[0] + " " + newPos[1]);
                if(newPos[0] > -1 && newPos[0] < height && newPos[1] > -1 && newPos[1] < width
                    && get(newPos[0], newPos[1]).getStep() < 0 && get(newPos[0], newPos[1]).getType() != 1)
                {
                    if(get(newPos[0], newPos[1]).getType() == 4)
                    {
                        int[] tempD = get(newPos[0], newPos[1]).getDestination();
                        if(get(tempD[0], tempD[1]).getStep() < 0)
                        {
                            get(tempD[0], tempD[1]).setStep(currentStep + 1);
                            get(tempD[0], tempD[1]).getDepartures().add(newPos);
                            que.add(tempD);
                            steps.add(currentStep + 1);
                        }
                    }
                    if(get(newPos[0], newPos[1]).getType() == 3)
                    {
                        //System.out.println("conveyBelt");
                        int direction = get(newPos[0], newPos[1]).getDirection();
                        int[] tempC = new int[] {newPos[0] + YPOS[direction], newPos[1] + XPOS[direction]};
                        //System.out.println(tempC[0] + ", " + tempC[1]);
                        while(tempC[0] > -1 && tempC[0] < height && tempC[1] > -1 && tempC[1] < width &&
                                get(tempC[0], tempC[1]).getType() == 3)
                        {
                            //System.out.println(tempC[0] + ", " + tempC[1]);
                            get(tempC[0], tempC[1]).setStep(currentStep + 1);
                            direction = get(tempC[0], tempC[1]).getDirection();
                            tempC = new int[] {tempC[0] + YPOS[direction], tempC[1] + XPOS[direction]};
                        }
                        if(tempC[0] > -1 && tempC[0] < height && tempC[1] > -1 && tempC[1] < width)
                        {
                            get(tempC[0], tempC[1]).setStep(currentStep + 1);
                            que.add(tempC);
                            steps.add(currentStep + 1);
                        }
                    }
                    get(newPos[0], newPos[1]).setStep(currentStep + 1);
                    que.add(newPos);
                    steps.add(currentStep + 1);
                    if(newPos[0] == endR && newPos[1] == endC)
                    {
                        testGrid();
                        return;
                    }
                }
            }
        }
        testGrid();
    }

    public void setPath(int endR, int endC)
    {
        path = new LinkedList<int[]>();
        Compo currentC = get(endR, endC);
        int cStep = currentC.getStep();
        int cR = endR;
        int cC = endC;
        path.add(new int[] {cR, cC});

        while(cStep > 0)
        {
            currentC = get(cR, cC);
            if(currentC.getType() == 5)
            {
                for(int i = 0; i < currentC.getDepartures().size(); i++)
                {
                    if(get(currentC.getDepartures().get(i)[0], currentC.getDepartures().get(i)[1]).getStep() == cStep)
                    {
                        cR = currentC.getDepartures().get(i)[0];
                        cC = currentC.getDepartures().get(i)[1];
                        path.add(new int[] {cR, cC});
                        continue;
                    }
                }
            }
            if(currentC.getType() == 3)
            {
                cR = cR + REYPOS[currentC.getDirection()];
                cC = cC + REXPOS[currentC.getDirection()];
                path.add(new int[] {cR, cC});
                if(get(cR, cC).getType() != 3)  // end of the belt
                {
                    cStep = cStep - 1;
                }
                continue;
            }
            for(int i = 0; i < 4; i++)
            {
                int[] newPos = {cR + YPOS[i], cC + XPOS[i]};
                if(newPos[0] > -1 && newPos[0] < height && newPos[1] > -1 && newPos[1] < width &&
                    (get(newPos[0], newPos[1]).getStep() == cStep) && (get(newPos[0], newPos[1]).getType() == 3))
                {
                    cR = newPos[0];
                    cC = newPos[1];
                    path.add(new int[] {cR, cC});
                }

                if(newPos[0] > -1 && newPos[0] < height && newPos[1] > -1 && newPos[1] < width &&
                    (get(newPos[0], newPos[1]).getStep() == cStep - 1))
                {
                    cStep = cStep - 1;
                    cR = newPos[0];
                    cC = newPos[1];
                    path.add(new int[] {cR, cC});
                    break;
                }
            }
        }
    }

    public LinkedList<int[]> getPath()
    {
        return path;
    }

    public void testGrid()
    {
        for(int r = 0; r < height; r++)
        {
            for(int c = 0; c < width; c++)
            {
                System.out.print(get(r, c).getStep() + ", ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void resetCompo()
    {
        for(int r = 0; r < height; r++)
        {
            for(int c = 0; c < width; c++)
            {
                get(r, c).setStep(-1);
            }
            System.out.println();
        }
    }
}
