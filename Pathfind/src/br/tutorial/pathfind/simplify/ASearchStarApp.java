package br.tutorial.pathfind.simplify;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.TreeSet;


public class ASearchStarApp {
	
	public interface Heuristic {
		public double heuristic(int sx, int sy, int dx, int dy);
	}
	
	public static class EuclidianHeuristic implements Heuristic {
		@Override
		public double heuristic(int sx, int sy, int dx, int dy) {
			return 0;
		}
	}
	
	public static class ManhattanHeuristic implements Heuristic {
		@Override
		public double heuristic(int sx, int sy, int dx, int dy) {
			double deltaX = Math.abs(dx - sx);
			double deltaY = Math.abs(dy - sy);
			return deltaX + deltaY;
		}
	}
	
	public static class Node implements Comparable<Node> {
		public double f, g, h, cost = 1;
		public int x, y;
		public Node parent;
		public boolean visited = false;
		public Node(Node parent, int x, int y, double f, double g, double h) {
			this.f = f;				// f = g+h, nos diz o custo total para alcancar o Node destino
			this.g = g;				// g mantem o custo necessario para alcancar um Node
			this.h = h;				// h indica qual o custo para alcancar o Node destino
			this.x = x;
			this.y = y;
			this.parent = parent;
		}
		
		public Node(int x, int y) {
			this(null, x, y, 0, 0, 0);
		}

		@Override
		public int compareTo(Node that) {
			if(this.f  < that.f) {
				return -1;
			} else if(this.f > that.f) {
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
	
	static Node [][] grid;											// mapa
	static boolean [][] visited;
	static ArrayList<Node> close 	= new ArrayList<>();			// lista dos nos ja visitados
	static SortedSet<Node> open 	= new TreeSet<>();				// lista dos nos a serem avaliados
	static PriorityQueue<Node> open2 	= new PriorityQueue<>();
	
	static final int BLOCKING = 0x0f;	// valor para indicar que uma celula do grid nao pode ser acessada
	
	static int width, height;										// dimensao do mapa
	
	public static void init(int w, int h) {
		width = w; height = h;
		grid = new Node[w][h];
		visited = new boolean[w][h];
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				grid[i][j] = new Node(null, i, j, 0, 0, 0);
			}
		}
	}
	
	public static void showGrid() {

	}
	
	public static boolean isVisited(int x, int y) {
		return visited[x][y];
	}
	
	public static ArrayList<Node> getNeighbors(Node n) {		
		return validadteStep(n.x, n.y);
	}
	
	private static ArrayList<Node> validadteStep(int x, int y) {
		ArrayList<Node> nb = new ArrayList<>();
		if(y<width) {
			nb.add(new Node(x, y+1));// direita
		}
		if(y>0) {
			nb.add(new Node(x, y-1));// esquerda
		}
		if(x<height) {
			nb.add(new Node(x+1, y));// baixo
		}
		if(x>0) {
			nb.add(new Node(x-1, y));// cima
		}
		return nb;
	}
	
	public static ArrayList<Node> search2(Node source, Node destiny) {
		ArrayList<Node> path = new ArrayList<>();
		open2.add(source);
		source.visited = true;
		Heuristic euclian = new EuclidianHeuristic();
		while(!open2.isEmpty()) {
			Node curr = open2.peek();
			if(curr.equals(destiny)) {
				// fazer o caminho reverso para verificar qual caminho foi feito
				Node node = curr;
				while(node.parent != null) {
					path.add(node);
					node = node.parent;
				}
				break;
			}
			
			open2.poll();
			close.add(curr);
			ArrayList<Node> neighboors = getNeighbors(curr);
			for(Node neighboor : neighboors) {
				if(close.contains(neighboor))		// no ja visitado
					continue;
				double gScore = curr.g + neighboor.cost;
				int x = neighboor.x,  y = neighboor.y;
				boolean beenVisited = neighboor.visited;
				// se esse vizinho ainda nao tinha sido visitado
				// ou se ele ja foi porem por um caminho mais custoso
				if( ! beenVisited || gScore < neighboor.g) {
					neighboor.visited = true;
					neighboor.parent = curr;
					neighboor.h = euclian.heuristic(curr.x, curr.y, x, y);
					neighboor.g = gScore;
					neighboor.f = neighboor.g + neighboor.f;
					// se ele nao tinha sido visitado, adiciona-lo na lista para um possivel caminho
					if(!beenVisited) {
						open2.add(neighboor);
					}
				}
			}
		}
		return path;
	}
	
	public static ArrayList<Node> search(Node source, Node destiny) {
		ArrayList<Node> path = new ArrayList<>();
		open.add(source);
		source.visited = true;
		Heuristic euclian = new EuclidianHeuristic();
		while(!open.isEmpty()) {
			Node curr = open.first();
			if(curr.equals(destiny)) {
				// fazer o caminho reverso para verificar qual caminho foi feito
				Node node = curr;
				while(node.parent != null) {
					path.add(node);
					node = node.parent;
				}
				break;
			}
			
			open.remove(curr);
			close.add(curr);
			ArrayList<Node> neighboors = getNeighbors(curr);
			for(Node neighboor : neighboors) {
				if(close.contains(neighboor))		// no ja visitado
					continue;
				double gScore = curr.g + neighboor.cost;
				int x = neighboor.x,  y = neighboor.y;
				boolean beenVisited = neighboor.visited;
				// se esse vizinho ainda nao tinha sido visitado
				// ou se ele ja foi porem por um caminho mais custoso
				if( ! beenVisited || gScore < neighboor.g) {
					neighboor.visited = true;
					neighboor.parent = curr;
					neighboor.h = euclian.heuristic(curr.x, curr.y, x, y);
					neighboor.g = gScore;
					neighboor.f = neighboor.g + neighboor.f;
					// se ele nao tinha sido visitado, adiciona-lo na lista para um possivel caminho
					if(!beenVisited) {
						open.add(neighboor);
					}
				}
			}
		}
		return path;
	}
	

	
	public static void main(String[] args) {
		init(10, 10);
		Node s = new Node(0,0);
		Node d = new Node(8,5);
		search2(s, d);
	}

}
