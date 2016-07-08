package main;

/*
 * 1. DNA is the size of the matrix. Depending on which cell, you perform an action regardless of what's in the cell.
 * 		- takes up less space
 * 		- faster to see results
 * 2. DNA is the size of all possible settings of the '3x3' block around it (excluding diags), perform action with that info
 * 		- myopic view makes it hard to see interesting behavior w/o leaving 'bread crumbs' everywhere
 * 		- more realistic in the sense of perceiving the environment 
 */

public class DNA {
	public static enum actions {
		UP, RIGHT, DOWN, LEFT, PICKUP, STAY;
	};
	private int[] genes;

	public DNA(int length){
		this.genes = new int[length];
		for(int  i = 0; i < this.genes.length; i++) {
			this.genes[i] = (int)(Math.random()*6);
		}
	}

	public DNA(int[] genes){
		this.genes = genes;
	}

	public int[] getGenes(){
		return this.genes;
	}

	public actions getAction(int num){
		switch(genes[num]){
			case 0:
				return actions.UP;
			case 1:
				return actions.RIGHT;
			case 2:
				return actions.DOWN;
			case 3:
				return actions.LEFT;
			case 4:
				return actions.PICKUP;
			case 5:
			default:
				return actions.STAY;
		}
	}
}
