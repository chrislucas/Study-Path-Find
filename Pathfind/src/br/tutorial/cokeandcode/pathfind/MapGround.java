package br.tutorial.cokeandcode.pathfind;

public class MapGround implements TileBasedMap {
	
	public int width;
	public int height;
	public int [][] grid, units;
	public boolean [][] visited;
	
	
	public MapGround(int width, int height) {
		this.height 	= height;
		this.width 		= width;
		this.grid 		= new int[width][height];
		this.units 		= new int[width][height];
		this.visited 	= new boolean[width][height];
		init();
	}
	
	private void init() {
		units[8][9] = TANK;
		units[1][2] = BOAT;
		units[9][9] = PLANE;
		fillGrid(0,0,5,5,WATER);
		fillGrid(0,5,3,8,WATER);
		fillGrid(6,7,5,5,TREES);
	}
	
	private void fillGrid(int x, int y, int w, int h, int type) {
		for(int i=x; i<x+width; i++) {
			for(int j=y; j<y+height; j++)
				grid[i][j] = type;
		}
	}
	
	public static final int GRASS = 0;
	public static final int WATER = 1;
	public static final int TREES = 2;
	public static final int PLANE = 3;
	public static final int BOAT = 4;
	public static final int TANK = 5;
	
	public boolean isVisited(int x, int y) {
		return this.visited[x][y];
	}
	
	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}
	
	public void setUnit(int x, int y, int unit) {
		this.units[x][y] = unit;
	}
	
	public int getUnit(int x, int y) {
		return this.units[x][y];
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		// TODO Auto-generated method stub
		this.visited[x][y] = true;
	}

	@Override
	public boolean blocked(Mover mover, int x, int y) {
		// TODO Auto-generated method stub
		if(getUnit(x, y) != 0) {
			return true;
		}
		
		int unit = ((EntityMover) mover).getType();
		if(unit == PLANE) {
			return false;
		}
		
		else if(unit == TANK) {
			return grid[x][y] != GRASS;
		}
		
		else if(unit == BOAT) {
			return grid[x][y] != WATER;
		}
		
		return false;
	}

	@Override
	public int getCost(Mover mover, int sx, int sy, int tx, int ty) {
		// TODO Auto-generated method stub
		return 1;
	}

}
