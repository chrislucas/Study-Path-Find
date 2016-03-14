package br.tutorial.cokeandcode.pathfind;

import java.util.ArrayList;

public class Path {

	private ArrayList<Step> steps = new ArrayList<>();
	
	public Path() {
	}
	
	public ArrayList<Step> getSteps() {
		return this.steps;
	}
	
	public int getLength() {
		return steps.size();
	}
	
	public Step getStep(int idx) {
		return steps.get(idx);
	}
	
	public void addStep(Step step) {
		steps.add(step);
	}
	
	public void addStep(double x, double y) {
		steps.add(new Step(x, y));
	}
	
	public void prependStep(double x, double y) {
		steps.add(0, new Step(x, y));
	}
	
	public boolean contains(double x, double y) {
		return steps.contains(new Step(x, y));
	}
	
	
	public static class Step {
		double x, y;
		static final double EPS = 10E-6;
		public Step(double x, double y) {
			this.x = x;
			this.y = y;
		}
		
		public static int compare() {
			return 0;
		}
		
		public static double euclidian(double cx, double cy, double tx, double ty) {
			double deltaX = tx - cx;
			double deltaY = ty - cy;
			return Math.sqrt( (deltaX * deltaX) + (deltaY * deltaY) );
		}
		
		public static double manhattan(double cx, double cy, double tx, double ty) {
			double deltaX = Math.abs(tx - cx);
			double deltaY = Math.abs(ty - cy);
			return deltaX + deltaY;
		}
	}
	
}
