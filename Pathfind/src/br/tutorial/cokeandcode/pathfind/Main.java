package br.tutorial.cokeandcode.pathfind;

import br.tutorial.cokeandcode.pathfind.Path.Step;

public class Main {

	public static void main(String[] args) {
		MapGround map = new MapGround(10, 10);
		
		PathFinder pathFinder = new AStarPathFinder(map, Integer.MAX_VALUE, false);
		Path path = pathFinder.findPath(new EntityMover(MapGround.TANK), 0, 0, 8, 5);
		if(path != null) {
			for(Step step : path.getSteps()) {
				
			}
		}
	}

}
