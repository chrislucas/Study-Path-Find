package br.tutorial.cokeandcode.pathfind;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class AStarPathFinder implements PathFinder {

	private ArrayList<Node> closed;
	private SortedSet<Node> open = new TreeSet<>();
	private TileBasedMap map;
	private int maxSearchDistance;
	
	private Node[][] nodes;
	private boolean allowDiagonalMovement;
	private AStarHeuristics heuristic;
	
	public AStarPathFinder(TileBasedMap map
			,int maxSearchDistance, boolean allowDiagonalMovement) {
		this(map, maxSearchDistance, allowDiagonalMovement, new EuclidianDistance());
	}
	
	public AStarPathFinder(TileBasedMap map, AStarHeuristics heuristic) {
		this.map = map;
		this.maxSearchDistance = Integer.MAX_VALUE - 1;
		this.allowDiagonalMovement = false;
		this.heuristic = heuristic;
		init();
	}
	
	public AStarPathFinder(TileBasedMap map
			,int maxSearchDistance, boolean allowDiagonalMovement, AStarHeuristics heuristic) {
		this.map = map;
		this.maxSearchDistance = maxSearchDistance;
		this.allowDiagonalMovement = allowDiagonalMovement;
		this.heuristic = heuristic;
		init();
	}
	
	private void init() {
		nodes = new Node[map.getWidth()][map.getHeight()];
		for(int i=0; i<map.getWidth(); i++) {
			for (int j = 0; j < map.getHeight(); j++) {
				nodes[i][j] = new AStarPathFinder.Node(i,j);
			}
		}
	}
	
	
	/*
	 * sx, sy, dx, dy  source x,y, destiny x,y
	 * */
	@Override
	public Path findPath(Mover mover, int sx, int sy, int dx, int dy) {
		
		if(map.blocked(mover, dx, dy))
			return null;
		
		nodes[sx][sy].cost 	= 0.0;
		nodes[sx][sy].depth = 0;
		closed.clear();
		open.clear();
		open.add(nodes[sx][sy]);
		nodes[dx][dy].parent = null;
		int maxDepth = 0;
		while( maxDepth  < maxSearchDistance && (open.size() != 0) ) {
			Node curr = open.first();
			if(curr.equals(nodes[dx][dy])) {
				break;
			}
			open.remove(curr);
			closed.add(curr);
			
			for(int i=-1; i<2; i++) {
				for(int j=-1; j<2; j++) {
					
					if(i == 0 && j == 0) {
						continue;
					}
					if(!allowDiagonalMovement) {
						if( i!=0 && j!=0)
							continue;
					}
					
					int nx = i + curr.x;
					int ny = j + curr.y;
					
					// eh um camninho valido de (sz,sy) para (nx. ny)
					if(isValidLocation(mover, sx, sy, nx, ny)) {
						// se sim o custo para chegar a esse ponto e a soma
						// de se chegar ao ponto corrente + novo ponto
						double nextStepCost = curr.cost + map.getCost(mover, curr.x
								,curr.y, nx, ny);
						//
						Node neighbour = nodes[nx][ny];
						map.pathFinderVisited(nx, ny);
						
						if(nextStepCost < neighbour.cost) {
							if(open.contains(neighbour)) {
								open.remove(neighbour);
							}
							if(closed.contains(neighbour)) {
								closed.remove(neighbour);
							}
						}
						
						if(!open.contains(neighbour) && !closed.contains(neighbour)) {
							neighbour.cost = nextStepCost;
							neighbour.heuristic = heuristic.getCost(map, mover, nx, ny, dx, dy);
							maxDepth = Math.max(maxDepth, neighbour.setParent(curr));
							open.add(neighbour);
						}
					}
				}
			}
		}
		// se nao houver um caminho retorne null
		if(nodes[dx][dy].parent == null) {
			return null;
		}
		
		
		/**
		 * Uma vez tendo um caminho, partimos do ponto 
		 * que queriamos alcaçar (destino), caminhado para tras
		 * nos nos parentes do destino, ate encontrar o ponto de origem da busca
		 * 
		 * */
		Path path = new Path();
		Node destiny = nodes[dx][dy];
		while(destiny != nodes[sx][sy]) {
			path.prependStep(destiny.x, destiny.y);
		}
		
		return null;
	}
	
	public boolean isValidLocation(Mover mover, int sx, int sy, int tx, int ty) {
		boolean valid = false;
		
		return valid;
	}
	
	public static class Node implements Comparable<Node> {
		private int x, y, depth;			// posicao no tanuleiro e profundade no tabuleiro
		private double cost;		// custo para ser alcancado
		private Node parent;		// no anterior
		private double heuristic;	//
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int setParent(Node parent) {
			this.depth = parent.depth + 1;
			this.parent = parent;
			return this.depth;
		}
		
		@Override
		public int compareTo(Node that) {
			double thisCost = this.heuristic + this.cost;
			double thatCost = that.heuristic + that.cost;
			if(thisCost < thatCost) {
				return -1;
			} else if(thisCost > thatCost) {
				return 1;
			}
			return 0;
		}
		
		@Override
		public boolean equals(Object o) {
			Node that = (Node) o;
			int cx = this.x, cy = this.y;
			int tx = that.x, ty = that.y;
			return cx == tx && cy == ty;
		}
		
	}

}
