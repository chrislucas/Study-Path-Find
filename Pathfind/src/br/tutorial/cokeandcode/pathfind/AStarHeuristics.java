package br.tutorial.cokeandcode.pathfind;

/*
 * Essa interface representa uma heuristica implementada na aplicacao.
 * Essa heuristica controla qual o melhor caminho durante busca pelo melhor caminho
 * 
 * */

public interface AStarHeuristics {
	public double getCost(TileBasedMap map, Mover mover, int x, int y, int tx, int ty);
}
