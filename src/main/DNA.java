package main;

import java.util.Arrays;

public class DNA {
	public static enum actions {
		UP, RIGHT, DOWN, LEFT, STAY;
	};
	
	private int[] genes;
	public static final int LENGTH = 625;
	
	public DNA(){
		this.genes = new int[LENGTH];
		for(int  i = 0; i < this.genes.length; i++) {
			this.genes[i] = (int)(Math.random()*5);
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
			default:
				return actions.STAY;
			case 1:
				return actions.UP;
			case 2:
				return actions.RIGHT;
			case 3:
				return actions.DOWN;
			case 4:
				return actions.LEFT;
		}
	}
	
	@Override
	public String toString(){
		return Arrays.toString(this.genes);
	}
}
