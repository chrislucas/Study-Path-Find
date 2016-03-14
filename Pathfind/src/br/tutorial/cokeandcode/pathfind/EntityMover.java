package br.tutorial.cokeandcode.pathfind;

public class EntityMover implements Mover {

	private int type;
	
	public EntityMover(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
	
}
