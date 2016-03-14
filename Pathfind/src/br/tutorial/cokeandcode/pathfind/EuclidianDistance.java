package br.tutorial.cokeandcode.pathfind;

public class EuclidianDistance implements AStarHeuristics {

	@Override
	public double getCost(TileBasedMap map, Mover mover, int x, int y, int tx, int ty) {
		return euclidian(x, y, tx, ty);
	}

	private static double euclidian(double cx, double cy, double tx, double ty) {
		double deltaX = tx - cx;
		double deltaY = ty - cy;
		return Math.sqrt( (deltaX * deltaX) + (deltaY * deltaY) );
	}
}
