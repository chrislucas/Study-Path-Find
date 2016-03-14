package br.tutorial.cokeandcode.pathfind;

/*
 * Esse eh o contrato entre o caminho que se eh percorrido num mapa e o mapa
 * */

public interface TileBasedMap {
	public int getWidth();
	public int getHeight();
	public void pathFinderVisited(int x, int y);
	public boolean blocked(Mover mover, int x, int y);
	public int getCost(Mover mover, int sx, int sy, int tx, int ty);
}
