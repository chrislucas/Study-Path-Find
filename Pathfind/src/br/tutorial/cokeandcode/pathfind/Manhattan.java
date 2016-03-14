package br.tutorial.cokeandcode.pathfind;

public class Manhattan implements AStarHeuristics {

	@Override
	public double getCost(TileBasedMap map, Mover mover, int x, int y, int tx, int ty) {
		return manhattan(x, y, tx, ty);
	}

	public static double manhattan(double cx, double cy, double tx, double ty) {
		double deltaX = Math.abs(tx - cx);
		double deltaY = Math.abs(ty - cy);
		return deltaX + deltaY;
	}
}
