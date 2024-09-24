package com.bslsk.gen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.bslsk.info.Assets;

public class LifeContext extends GContext {

	public boolean[][] grid;
	int width, height; 
	int rate;
	
	BufferedImage lifeB;
	Graphics2D lG;
	public LifeContext(int w, int h, int r)
	{
		width = w;
		height = h;
		lifeB = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		lG = (Graphics2D)lifeB.getGraphics();
		lG.setBackground(new Color(0,0,0,0));
		//lG.setBackground(Color.TRANSLUCENT);
		generateGrid();
	}
	@Override
	public void draw(Graphics2D g) {
		lG.clearRect(0, 0, Assets.WIDTH, Assets.HEIGHT);
		//g.buffer.setColor(g.current);
		lG.setColor(Color.BLACK);
		for(int x = 0; x < Assets.WIDTH/10; x ++)
			for(int y = 0; y < Assets.HEIGHT/10; y++)
			{
				if(grid[x][y] == true)
					lG.fillRect(x*10, y*10, 10, 10);//buffer.fillRect(x*10, y*10, 10, 10);
				else
					lG.clearRect(x*10, y*10, 10, 10);//buffer.fillRect(x*10, y*10, 10, 10);
			}
		g.drawImage(lifeB, 0, 0, Assets.WIDTH, Assets.HEIGHT, null);

	}

	@Override
	public void step() {
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

	@Override
	public void modify(int amount)
	{

	}

	@Override
	public void addNext()
	{

	}

	@Override
	public boolean removeLast()
	{
		return false;
	}

	private boolean isSame(boolean[][] a, boolean[][] b)
	{
		for(int x =0; x < a.length;x++)
			for(int y =0; y < a[x].length;y++)
				if(a[x][y] != b[x][y])return false;
		return true;
	}
	public void generateGrid()
	{
		grid = new boolean[width][height];
		Random r1 = new Random();
		int t = 4000;
		for(int x = 0; x < t; x ++)
			grid[r1.nextInt(width)][r1.nextInt(height)] = true;
	}

}
