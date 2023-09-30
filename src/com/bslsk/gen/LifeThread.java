package com.bslsk.gen;

import java.util.Random;

public class LifeThread implements Runnable 
{
	public boolean[][] grid;
	int width, height; 
	int rate;
	public LifeThread(int w, int h, int r)
	{
		width = w;
		height = h;
		rate = r;
		generateGrid();
		
	}
	public void generateGrid()
	{
		grid = new boolean[width][height];
		Random r1 = new Random();
		int t = 4000;
		for(int x = 0; x < t; x ++)
			grid[r1.nextInt(width)][r1.nextInt(height)] = true;
	}
	public void run()
	{
		
		while(true)
		{
			nextGeneration();
			try {Thread.sleep(rate); }
			catch(InterruptedException ie) {}
		}
	}
	public void nextGeneration()
    {
        boolean[][] future = new boolean[width][height];
 
        // Loop through every cell
        for (int l = 0; l < grid.length; l++)
        {
            for (int m = 0; m < grid[0].length; m++)
            {
                // finding no Of Neighbours that are alive
                int aliveNeighbours = 0;
                for (int i = -1; i <= 1; i++)
                    for (int j = -1; j <= 1; j++)
                      if ((l+i>=0 && l+i<grid.length) && (m+j>=0 && m+j<grid[0].length) && grid[l + i][m + j])
                        aliveNeighbours ++;
 
                // The cell needs to be subtracted from
                // its neighbours as it was counted before
                if(grid[l][m]) aliveNeighbours --;
 
                // Implementing the Rules of Life
 
                // Cell is lonely and dies
                if ((grid[l][m]) && (aliveNeighbours < 2))
                    future[l][m] = false;
 
                // Cell dies due to over population
                else if ((grid[l][m]) && (aliveNeighbours > 3))
                    future[l][m] = false;
 
                // A new cell is born
                else if (!(grid[l][m]) && (aliveNeighbours == 3))
                    future[l][m] = true;
 
                // Remains the same
                else
                    future[l][m] = grid[l][m];
            }
        }
        
        if(isSame(grid,future))
        {
        	grid = future;
        }
        else
        	generateGrid();
    }
	private boolean isSame(boolean[][] a, boolean[][] b) 
	{
		for(int x =0; x < a.length;x++)
			for(int y =0; y < a[x].length;y++)
				if(a[x][y] != b[x][y])return false;
		return true;
	}
	public void dump() 
	{
		for (int l = 0; l < grid.length; l++)
        {
            for (int m = 0; m < grid[l].length; m++)
            {
            	if(grid[l][m]) System.out.print("[X]");
            	else if(!grid[l][m]) System.out.print("[ ]");
            }
            System.out.print("\n");
        }
	}
}
